
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Список пользователей</h1>
<div class="w-100 d-flex justify-content-center"  style="margin: 15px">
  <c:forEach var="entry" items="${mapPersons}">
    <div class="card" style="width: 18rem;">
      <div class="card-body">
        <h5 class="card-title">Логин: ${entry.key.login}</h5>
            <h6 class="card-subtitle">Имя: ${entry.key.user.name}</h6>
            <p class="card-subtitle">Фамилия: ${entry.key.user.surname}</p>
            <p class="card-text">Номер телефона: ${entry.key.user.tel}</p>
            <p class="card-text">Количество денег: ${entry.key.user.amountMoney}</p>
      </div>
    </div>
  </c:forEach>
</div>

