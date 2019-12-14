<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="bs">
	<nav class="navbar navbar-expand-md navbar-dark bg-dark">
		<a href="#" class="navbar-brand">My application</a>
		<button type="button" class="navbar-toggler" data-toggle="collapse"
			data-target="#navbarCollapse">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarCollapse">
			<div class="navbar-nav">
				<a href="${pageContext.request.contextPath}/"
					class="nav-item nav-link active">Home</a> <a href="#"
					class="nav-item nav-link">About</a> <a href="#"
					class="nav-item nav-link">Something new</a>
			</div>
			<form class="form-inline ml-auto">
				<input type="hidden" id="searchAction" name="searchAction" value="searchByName" /> 
					<input class="form-control py-2" name="personName" id="personName" type="search"
					placeholder="Type the Name or Surname" required="required"> 
					<span class="input-group-append">
						<button class="btn btn-outline-light" type="submit">
							<i class="fa fa-search"></i>Search
						</button>
					</span>
			</form>
		</div>
	</nav>
	<c:if test="${not empty message}">                
    <div class="alert alert-success">
        ${message}
    </div>
</c:if> 
</div>