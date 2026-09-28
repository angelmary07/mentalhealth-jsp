package mentalhealth;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/patients")
public class patientservlet extends HttpServlet {

    private final patientdao dao = new patientdao();

    // list page, search (?q=) and edit (?action=edit&id=)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            if ("edit".equals(req.getParameter("action"))) {
                patient p = dao.getbyid(req.getParameter("id"));
                if (p != null) {
                    req.setAttribute("editpatient", p);
                    req.setAttribute("editing", true);
                }
            }
            showpage(req, resp);
        } catch (SQLException ex) {
            fail(req, resp, ex);
        }
    }

    // insert (save), update and delete
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        try {
            if ("delete".equals(action)) {
                dao.delete(req.getParameter("id"));
                flash(req, "Patient deleted.");
                resp.sendRedirect("patients");
                return;
            }

            boolean editing = "update".equals(action);
            patient p = readform(req);
            String error = validate(p);

            if (error == null) {
                try {
                    if (editing) {
                        if (dao.update(p)) {
                            flash(req, "Patient updated successfully.");
                        } else {
                            error = "Patient not found, it may have been deleted.";
                        }
                    } else {
                        dao.insert(p);
                        flash(req, "Patient added successfully.");
                    }
                } catch (SQLIntegrityConstraintViolationException ex) {
                    error = "A patient with this ID already exists.";
                }
            }

            if (error != null) {
                req.setAttribute("error", error);
                req.setAttribute("editpatient", p);
                req.setAttribute("editing", editing);
                showpage(req, resp);
                return;
            }

            resp.sendRedirect("patients");

        } catch (SQLException ex) {
            fail(req, resp, ex);
        }
    }

    private patient readform(HttpServletRequest req) {

        patient p = new patient();
        p.setid(clean(req.getParameter("id")));
        p.setname(clean(req.getParameter("name")));

        try {
            p.setage(Integer.parseInt(clean(req.getParameter("age"))));
        } catch (NumberFormatException ex) {
            p.setage(0);
        }

        p.setgender(orelse(req.getParameter("gender"), "Not Specified"));
        p.setmood(orelse(req.getParameter("mood"), "Calm"));
        p.setseverity(orelse(req.getParameter("severity"), "Not Specified"));

        String[] picked = req.getParameterValues("symptoms");
        StringJoiner sj = new StringJoiner(", ");
        if (picked != null) {
            for (String s : picked) sj.add(s);
        }
        p.setsymptoms(sj.length() == 0 ? "None" : sj.toString());

        // an unchecked toggle sends nothing, so missing means inactive
        p.setstatus(req.getParameter("status") != null ? "Active" : "Inactive");

        return p;
    }

    private String validate(patient p) {
        if (p.getid().isEmpty() || p.getname().isEmpty()) {
            return "ID and Name are required.";
        }
        if (p.getid().length() > 10) {
            return "ID can be at most 10 characters.";
        }
        if (p.getage() < 1 || p.getage() > 120) {
            return "Age must be a number between 1 and 120.";
        }
        return null;
    }

    private void showpage(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        String q = req.getParameter("q");
        List<patient> all = dao.getall(null);

        int active = 0;
        int high = 0;
        for (patient p : all) {
            if ("Active".equals(p.getstatus())) active++;
            if ("High".equals(p.getseverity())) high++;
        }

        boolean searching = q != null && !q.trim().isEmpty();
        req.setAttribute("patients", searching ? dao.getall(q) : all);
        req.setAttribute("total", all.size());
        req.setAttribute("active", active);
        req.setAttribute("high", high);
        req.setAttribute("q", q);

        HttpSession session = req.getSession();
        Object msg = session.getAttribute("msg");
        if (msg != null) {
            req.setAttribute("msg", msg);
            session.removeAttribute("msg");
        }

        req.getRequestDispatcher("/patients.jsp").forward(req, resp);
    }

    private void fail(HttpServletRequest req, HttpServletResponse resp, SQLException ex)
            throws ServletException, IOException {
        req.setAttribute("error", "Database error: " + ex.getMessage());
        req.setAttribute("patients", new ArrayList<patient>());
        req.getRequestDispatcher("/patients.jsp").forward(req, resp);
    }

    private void flash(HttpServletRequest req, String message) {
        req.getSession().setAttribute("msg", message);
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private String orelse(String value, String fallback) {
        return (value == null || value.isEmpty()) ? fallback : value;
    }
}
