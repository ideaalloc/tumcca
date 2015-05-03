<#-- @ftlvariable name="userDetail" type="com.qicaima.api.model.admin.UserDetail" -->

<div class="container">
    <div class="form-horizontal">
        <div class="form-group">
            <label class="control-label col-sm-2" for="shopName">门店</label>

            <div class="col-sm-10">
                <p id="shopName" class="form-control-static">${userDetail.shopName}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="username">用户名</label>

            <div class="col-sm-10">
                <p id="username" class="form-control-static">${userDetail.username}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="credit">积分</label>

            <div class="col-sm-10">
                <p id="credit" class="form-control-static">${userDetail.credit}</p>
            </div>
        </div>
    </div>
</div>
