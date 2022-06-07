
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Доходы</h1>
<div class="w-100 d-flex justify-content-center"  style="margin: 15px">
    <form action="incomePerMonth" method="POST">
        <div class="card" style="width: 30rem;">
          <div class="card-body">
              <div class="mb-3">

                <h1 class="card-subtitle">Доходы за все время: </h1>
                <h2 class="card-subtitle" name="income">${income} EUR</h2>
              </div>

          </div>
          <div class="card-body">
              <div class="mb-3">

                <h1 class="card-subtitle">Доходы за определенный месяц: </h1>
                <select name="chosenMonth" class="form-select" aria-label="months">
                    <option selected>Выберите месяц</option>
                    <option value="1">Январь</option>
                    <option value="2">Февраль</option>
                    <option value="3">Март</option>
                    <option value="4">Апрель</option>
                    <option value="5">Май</option>
                    <option value="6">Июнь</option>
                    <option value="7">Июль</option>
                    <option value="8">Август</option>
                    <option value="9">Сентябрь</option>
                    <option value="10">Октябрь</option>
                    <option value="11">Ноябрь</option>
                    <option value="12">Декабрь</option>
                </select>
              </div>
                <div class="row">
                    <button type="submit" class="btn btn-primary">Показать доход за выбранный месяц</button>
                </div>
          </div>
        </div>  
    </form>
</div>