
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Список обуви</h1>
<div class="w-100 d-flex justify-content-center"  style="margin: 15px">
  <c:forEach var="entry" items="${mapModels}">
    <div class="card" style="width: 18rem;">
    <img src="insertFile/${entry.value.fileName}" class="card-img-top" >
      <div class="card-body">
        <h5 class="card-title">Модель: ${entry.key.name}</h5>
        <h6 class="card-subtitle">Брэнд: ${entry.key.brand}</h6>
        <p class="card-text">Количество: ${entry.key.quantity}</p>
        <p class="card-text">Размер: ${entry.key.size}</p>
        <p class="card-text">Цена: ${entry.key.price}</p>
        <p class="card-text"><a href="takeOnModel?modelId=${entry.key.id}">Купить</a></p>
      </div>
    </div>
  </c:forEach>
</div>