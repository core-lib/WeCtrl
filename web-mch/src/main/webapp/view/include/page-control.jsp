<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: yangchangpei
  Date: 17/3/2
  Time: 上午11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="paginations" scope="page">
    <fmt:formatNumber value="${(page.total / page.capacity) - ((page.total / page.capacity) % 1 == 0 ? 0 : 0.5) + 1}" type="number" pattern="#"/>
</c:set>
<ul class="pagination">
    <c:choose>
        <c:when test="${page.pagination == 0}">
            <li class="disabled"><a>&laquo;</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="javascript: toPage(${page.pagination - 1});">&laquo;</a></li>
        </c:otherwise>
    </c:choose>
    <c:forEach begin="1" end="${paginations}" var="pagination" varStatus="status">
        <c:choose>
            <c:when test="${pagination == 1 || status.last}">
                <li ${page.pagination == pagination - 1 ? 'class="active"' : ''}><a href="javascript: toPage(${pagination - 1});">${pagination}</a></li>
            </c:when>
            <c:when test="${page.pagination - pagination < 3 && pagination - page.pagination < 5}">
                <li ${page.pagination == pagination - 1 ? 'class="active"' : ''}><a href="javascript: toPage(${pagination - 1});">${pagination}</a></li>
            </c:when>
            <c:when test="${page.pagination - pagination == 3 || pagination - page.pagination == 5}">
                <li class="disabled"><a>...</a></li>
            </c:when>
        </c:choose>
    </c:forEach>
    <c:choose>
        <c:when test="${page.pagination == paginations - 1}">
            <li class="disabled"><a>&raquo;</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="javascript: toPage(${page.pagination + 1});">&raquo;</a></li>
        </c:otherwise>
    </c:choose>
</ul>
<script>
    function toPage(pagination) {
        var href = location.href;
        if (href.indexOf("pagination=") != -1) {
            href = href.replace(/pagination=\d*/, "pagination=" + pagination);
            location.href = href;
        } else {
            if (href.indexOf("?") != -1) {
                location.href = href + "&pagination=" + pagination;
            } else {
                location.href = href + "?pagination=" + pagination;
            }
        }
    }
</script>