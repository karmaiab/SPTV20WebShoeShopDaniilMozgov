
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h3 class="w-100 text-center my-5">Загрузить файл</h3>
<div class="w-100 d-flex justify-content-center">
    <div class="card border-0 p-5 m-4" style="width: 30rem;">
        <form action="uploadCover" method="POST"  enctype="multipart/form-data">
            <div class="row mb-3">
              <label for="file" class="form-label">Выберите локальный файл</label>
              <input class="form-control" type="file" name="file" id="file">
            </div>
            <div class=" row mb-3">
              <label for="description" class="form-label">Описание</label>
              <input class="form-control" type="text" name="description" id="description">
            </div>
            <div class="row">
                <button type="submit" class="btn btn-primary">Загрузить файл</button>
            </div>
        </form>
    </div>
</div>
