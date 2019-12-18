<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="by.javacourcee.hotel.RoomType"%>

<form action="/" method="post" id="rooms" role="form">
	<input type="hidden" id="action" name="action" value="${action}" />
	<input type="hidden" id="roomId" name="roomId" value="${room.id}" />
	<div class="container">
	</div>
	
	<c:forEach items="${rooms}" var="roomByTypeRow">
	<c:choose>
		<c:when test="${not empty roomByTypeRow}">
			<c:set var="key" value="${roomByTypeRow.key}"/>
			<% 
				out.print(RoomType.getRoomType((Integer)pageContext.getAttribute("key")));
			%>
			<table class="table">
			<c:forEach begin="0" items="${roomByTypeRow.value}" var="room" varStatus="loop">
				<c:choose>
					<c:when test="${loop.index % 3 eq 0}">
						<c:set var="curIndex" value="${loop.index}" />
						<c:if test="${not empty curIndex}">
							<c:forEach begin="${curIndex}" end="${curIndex+2}"
								items="${roomByTypeRow.value}" var="room" varStatus="loop">
								<td>
									<div class="card" id="cardNo${loop.index}">
										<div class="card-body">
											<h4 class="card-title">${room.id}</h4>
											<h5 class="card-subtitle mb-2 text-muted">${room.status}</h5>
											<p class="card-text">${room.pricePerDay}</p>
											<!--a href="buyProduct?code=${product.code}" class="card-link">buy</a-->
										</div>
									</div>
								</td>
							</c:forEach>
						</c:if>
					</c:when>
					<c:otherwise>
					<tr/>
					</c:otherwise>
				</c:choose>
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





