new Vue({
    el: '#app',
    data: {
        allItems: []
    },

    mounted() {
        this.getItems();
    },

    methods: {
        getItems() {
            const t = this;
            $.ajax({
                url: "/getItems",
                dataType: "json",
                success: function (response) {
                    t.allItems = response;
                },
                error: function () {
                    alert("页面加载失败!")
                },
            })
        },

        download(data) {
            const $form = $("<form></form>").attr("method", "get");
            $form.append($("<input>").attr("name", "name").val(data.name));
            $form.append($("<input>").attr("name", "columnDefine").val(data.columnDefine));
            $form.append($("<input>").attr("name", "sql").val(data.sql));
            $form.attr("action", `/excelDownload`);
            $("body").append($form);
            $form.submit().remove();
        }
    }
})