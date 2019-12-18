<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="/" method="post" id="rooms" role="form">
	<input type="hidden" id="action" name="action" value="${action}" />
	<input type="hidden" id="roomId" name="roomId" value="${person.id}" />
	<c:forEach items="${rooms}" var="roomByTypeRow">
	<c:choose>
		<c:when test="${not empty roomByTypeRow}">
			<c:out value="${roomByTypeRow.key}"/>
			<table class="table" aria-describedby="roomsTable">
				<thead class="thead-dark">
					<tr>
						<th scope="col">#</th>
						<th scope="col">Status</th>
						<th scope="col">Price per Day</th>
						<th scope="col">Username</th>
						<th scope="col">houseKeepingStatus</th>
						<th scope="col"></th>
					</tr>
				</thead>
				<c:forEach var="room" items="${roomByTypeRow.value}">
					<c:set var="classSucess" value="" />
					<c:if test="${id == room.id}">
						<c:set var="classSucess" value="info" />
					</c:if>
					<tr class="${classSucess}">
						<td>${room.id}</td>
						<td>${room.status}</td>
						<td>${room.pricePerDay}</td>
						<td>${room.houseKeepingStatus}</td>
						
						
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<br>
			<div class="alert alert-info">No people found matching your	search criteria</div>
		</c:otherwise>
	</c:choose>
	
	</c:forEach>
	
</form>



<nav aria-label="Navigation">
    <ul class="pagination">
        <c:if test="${currentPage != 1}">
            <li class="page-item">
            	<a class="page-link" href="${pageContext.request.contextPath}/?role=${role}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">Previous</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="page-item active">
                    	<a class="page-link"> ${i} <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                    	<a class="page-link" href="?role=${role}&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage lt noOfPages}">
            <li class="page-item">
            	<a class="page-link" href="?role=${role}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
            </li>
        </c:if>              
    </ul>
</nav>





