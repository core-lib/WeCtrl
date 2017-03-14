function onEnvironmentTabShow(appID, envKey) {
    $.showLoading("请稍候...");
    $.ajax({
        url: "/applications/" + appID + "/environments/" + envKey + "/acquiescence",
        type: "GET",
        success: function (res) {
            $.hideLoading();
            var $acquiescents = $("#" + envKey).find("input[name='acquiescent']");
            $acquiescents.each(function (index, element) {
                if ((res.entity == true && $(this).val() == 'true') || (res.entity == false && $(this).val() == 'false')) {
                    $(this).prop("checked", true);
                } else {
                    $(this).prop("checked", false);
                }
            });
        },
        error: function (res) {
            $.hideLoading();
        }
    });
}

function onDeleteButtonTap(appID, envKey) {
    $.confirm("确定删除该应用环境?", "注意", function () {
        $.showLoading("正在删除...");
        $.ajax({
            type: "DELETE",
            url: "/applications/" + appID + "/environments/" + envKey,
            success: function (res) {
                $.hideLoading();
                if (res.success) {
                    $("li[envKey='" + envKey + "'],div[envKey='" + envKey + "']").remove();
                    $("li[envKey]:eq(0)").addClass("active").addClass("in");
                    $("div[envKey]:eq(0)").addClass("active").addClass("in");
                    $.toast("删除成功", "success");
                } else {
                    $.alert(res.message ? res.message : "删除失败", "注意");
                }
            },
            error: function (res) {
                $.hideLoading();
                $.alert("删除失败", "注意");
            }
        });
    }, function () {

    });
}

function onPostButtomTap(form) {
    $.showLoading("请稍候...");
    $.ajax({
        type: "POST",
        url: form.action,
        data: $(form).serialize(),
        success: function (res) {
            $.hideLoading();
            if (res.success) {
                location.href = res.entity;
            } else {
                var tpl = "{{#entity}}" + $("#error-tpl").html() + "{{/entity}}";
                var html = Mustache.render(tpl, res);
                $(form).find(".error-container").empty().html(html);
            }
        },
        error: function (res) {
            $.hideLoading();
            res = {
                entity: ["未知错误"]
            };
            var tpl = "{{#entity}}" + $("#error-tpl").html() + "{{/entity}}";
            var html = Mustache.render(tpl, res);
            $(form).find(".error-container").empty().html(html);
        }
    });
    return false;
}

function onPutButtonTap(form) {
    $.showLoading("请稍候...");
    $.ajax({
        type: "PUT",
        url: form.action,
        data: $(form).serialize(),
        success: function (res) {
            $.hideLoading();
            if (res.success) {
                form.action = res.entity;
                res = {
                    entity : ["更新成功"]
                }
                var tpl = "{{#entity}}" + $("#success-tpl").html() + "{{/entity}}";
                var html = Mustache.render(tpl, res);
                $(form).find(".error-container").empty().html(html);
            } else {
                var tpl = "{{#entity}}" + $("#error-tpl").html() + "{{/entity}}";
                var html = Mustache.render(tpl, res);
                $(form).find(".error-container").empty().html(html);
            }
        },
        error: function (res) {
            $.hideLoading();
            res = {
                entity: ["未知错误"]
            };
            var tpl = "{{#entity}}" + $("#error-tpl").html() + "{{/entity}}";
            var html = Mustache.render(tpl, res);
            $(form).find(".error-container").empty().html(html);
        }
    });
    return false;
}