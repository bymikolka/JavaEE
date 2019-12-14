<%@page import="by.javaeecources.repository.PersonFactory.PersonRole"%>
<%@page import="by.javaeecources.repository.PersonFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">
	<div class="d-flex h-100">
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text" id="basic-addon3">Select a Role:</span>
			</div>
			<form action="#/" method="get">
				<select class="btn btn-outline-dark" name="role">
					<c:forEach items="${personRoles}" var="option">
						<option value="${option.role}"
							${param.role == option.role? 'selected' : ''}>${option.name}</option>
					</c:forEach>
				</select>
				<button type="submit" class="btn btn-primary btn-md">Select</button>
				<div class="text-right"></div>
			</form>
		</div>
	</div>
</div>
<div class="text-right">
<form action ="${pageContext.request.contextPath}/create">         
    <button type="submit" class="btn btn-primary btn-md">New person</button> 
</form>
</div>

<script>
function deletePerson(id)
{
	document.getElementById('idPerson').value=id;
	document.getElementById('action').value='delete';
	document.getElementById('personForm').submit();
}
</script>
<form action="/" method="post" id="personForm" role="form">
	<input type="hidden" id="action" name="action" value="${action}" /> 
	<input
		type="hidden" id="idPerson" name="idPerson" value="${person.id}" />
	<c:choose>
		<c:when test="${not empty personList}">
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Full name</th>
						<th scope="col">Role</th>
						<th scope="col">Description</th>
						<th scope="col" >E-mail</th>
						<th scope="col">Username</th>
						<th scope="col"></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<c:forEach var="person" items="${personList}">
					<c:set var="classSucess" value="" />
					<c:if test="${idPerson == person.id}">
						<c:set var="classSucess" value="info" />
					</c:if>
					<tr class="${classSucess}">
						<td>${person.firstname}&nbsp;${person.surname}</td>
						<c:set var="tRole" value="${person.role}" />
						<td>
							<%
								out.print(PersonFactory.getPersonTypeByRole((Long) pageContext.getAttribute("tRole"))
													.getShortName());
							%>
						</td>
						<td>${person.description}</td>
						<td>${person.email}</td>
						<td>${person.username}</td>
						<td><a href="?idPerson=${person.id}&searchAction=searchById">edit</a></td>
						<td><a href="?idPerson=${person.id}&searchAction=delete"
							id="delete"
							onclick="deletePerson(${person.id})">delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<br>
			<div class="alert alert-info">No people found matching your	search criteria</div>
		</c:otherwise>
	</c:choose>
</form>
