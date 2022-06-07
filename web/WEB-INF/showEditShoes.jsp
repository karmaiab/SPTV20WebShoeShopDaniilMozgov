
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Редактирование обуви</h1>
<div class="w-100 d-flex justify-content-center">
    <div class="card border-0 p-5 m-4" style="width: 30rem;">
            <form action="editModel" method="POST">
                <input hidden type="text" name="id" value="${id}" class="text-muted mb-0">
              <div class="mb-3">
                <label>Модель</label>
                <input type="text" name="model2" value="${model2}" class="text-muted mb-0">
              </div>
              <div class="mb-3">
                <label>Брэнд</label>
                <input type="text" name="brand2" value="${brand2}" class="text-muted mb-0">
              </div>
              <div class="mb-3">
                <label>Цена</label>
                <input type="text" name="price2" value="${price2}" class="text-muted mb-0">
              </div>
              <div class="mb-3">
                <label>Размер</label>
                <input type="text" name="size2" value="${size2}" class="text-muted mb-0">
              </div>
              <div class=mb-3">
                <label>Количество</label>
                <input type="text"name="quantity2" value="${quantity2}" class="text-muted mb-0">
              </div>
            Обложка
            <select name="coverId2" class="form-select" aria-label="Выберите обложку">
                <c:forEach var="cover" items="${covers2}">
                    <option value="${cover.id}">${cover.description}</option>
                </c:forEach>
            </select>
              <div class="mb-3">
                <button type="submit" class="btn btn-primary">Сохранить изменения</button>
              </div>
            </form>
    </div>
</div>