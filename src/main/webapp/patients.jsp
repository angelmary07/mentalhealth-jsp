<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mental Health Tracking System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Mental Health Tracking System</h1>

    <c:if test="${not empty msg}">
        <div class="flash success"><c:out value="${msg}"/></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="flash error"><c:out value="${error}"/></div>
    </c:if>

    <div class="stats">
        <span>Total: <strong><c:out value="${total}"/></strong></span>
        <span>Active: <strong><c:out value="${active}"/></strong></span>
        <span>High Severity: <strong><c:out value="${high}"/></strong></span>
    </div>

    <div class="panel">
        <h2><c:out value="${editing ? 'Edit Patient' : 'Add Patient'}"/></h2>
        <form method="post" action="patients" class="form-grid">
            <input type="hidden" name="action" value="${editing ? 'update' : 'save'}"/>

            <label>ID</label>
            <input type="text" name="id" maxlength="10" value="${editpatient.id}" ${editing ? 'readonly' : ''} required>

            <label>Name</label>
            <input type="text" name="name" maxlength="50" value="${editpatient.name}" required>

            <label>Age</label>
            <input type="number" name="age" min="1" max="120" value="${editpatient.age}" required>

            <label>Gender</label>
            <select name="gender">
                <option ${editpatient.gender == 'Female' ? 'selected' : ''}>Female</option>
                <option ${editpatient.gender == 'Male' ? 'selected' : ''}>Male</option>
                <option ${editpatient.gender == 'Other' ? 'selected' : ''}>Other</option>
                <option ${editpatient.gender == 'Not Specified' ? 'selected' : ''}>Not Specified</option>
            </select>

            <label>Mood</label>
            <select name="mood">
                <option ${editpatient.mood == 'Calm' ? 'selected' : ''}>Calm</option>
                <option ${editpatient.mood == 'Anxious' ? 'selected' : ''}>Anxious</option>
                <option ${editpatient.mood == 'Stressed' ? 'selected' : ''}>Stressed</option>
                <option ${editpatient.mood == 'Low' ? 'selected' : ''}>Low</option>
            </select>

            <label>Severity</label>
            <select name="severity">
                <option ${editpatient.severity == 'Low' ? 'selected' : ''}>Low</option>
                <option ${editpatient.severity == 'Medium' ? 'selected' : ''}>Medium</option>
                <option ${editpatient.severity == 'High' ? 'selected' : ''}>High</option>
                <option ${editpatient.severity == 'Not Specified' ? 'selected' : ''}>Not Specified</option>
            </select>

            <label>Symptoms</label>
            <div class="inline">
                <label><input type="checkbox" name="symptoms" value="Anxiety"> Anxiety</label>
                <label><input type="checkbox" name="symptoms" value="Insomnia"> Insomnia</label>
                <label><input type="checkbox" name="symptoms" value="Fatigue"> Fatigue</label>
                <label><input type="checkbox" name="symptoms" value="Irritability"> Irritability</label>
            </div>

            <label>Status</label>
            <label class="inline"><input type="checkbox" name="status" ${editpatient.status == 'Active' ? 'checked' : ''}> Active</label>

            <div class="actions">
                <button type="submit">${editing ? 'Update' : 'Save'}</button>
                <c:if test="${editing}">
                    <a href="patients">Cancel</a>
                </c:if>
            </div>
        </form>
    </div>

    <div class="panel">
        <h2>Patients</h2>
        <form method="get" action="patients" class="search">
            <input type="text" name="q" value="${q}" placeholder="Search by ID or name">
            <button type="submit">Search</button>
            <a href="patients">Reset</a>
        </form>

        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Gender</th>
                <th>Mood</th>
                <th>Severity</th>
                <th>Symptoms</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${patients}" var="p">
                <tr>
                    <td><c:out value="${p.id}"/></td>
                    <td><c:out value="${p.name}"/></td>
                    <td><c:out value="${p.age}"/></td>
                    <td><c:out value="${p.gender}"/></td>
                    <td><c:out value="${p.mood}"/></td>
                    <td><c:out value="${p.severity}"/></td>
                    <td><c:out value="${p.symptoms}"/></td>
                    <td><c:out value="${p.status}"/></td>
                    <td>
                        <a href="patients?action=edit&id=${p.id}">Edit</a>
                        <form method="post" action="patients" class="inline-form">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="id" value="${p.id}"/>
                            <button type="submit" onclick="return confirm('Delete this patient?');">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty patients}">
                <tr><td colspan="9">No patients found.</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
