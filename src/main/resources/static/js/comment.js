/**
 * 提交评论
 */
function commentPost() {
    var questionId = $("#question_id").val();
    var commentText = $("#comment_text").val();
    comment2target(questionId, commentText, 1)
}

/**
 * 提交回复
 * @param e
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var commentText = $("#input-" + commentId).val();
    comment2target(commentId, commentText, 2)
}

/**
 * 提交回复和评论封装方法
 * @param targetId
 * @param commentText
 * @param type
 */
function comment2target(targetId, commentText, type) {
    if (!commentText) {
        alert("回复的内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/comment",
        data: JSON.stringify({
            "parentId": targetId,
            "comment": commentText,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                //重新加载页面
                window.location.reload();
                // $("#comment_module").hide();
            } else {
                //当出现未登录时
                if (response.code == 2003) {
                    var conf = confirm(response.message);
                    if (conf) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.6c4f6f2053e11699&redirect_uri=http://localhost:8887/callback&state=1")
                        window.localStorage.setItem("open", conf);
                    }
                } else {
                    //出现其他错误
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}


/**
 * 展开二级评论
 */
function collapseComments(e) {
    //获取评论的id
    var id = e.getAttribute("data-id");
    //获取二级评论的div
    var comments = $("#comment-" + id)
    //获取二级评论状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //关闭二级评论
        comments.removeClass("in");
        //移除二级评论状态
        // e.classList.remove()移除class
        e.removeAttribute("data-collapse");
        $("#comment-" + id).empty();

    } else {


        if (comments.children().length != 0) {
            //展开二级评论
            comments.addClass("in");
            // e.classList.add()添加class
            //设置二级评论状态
            e.setAttribute("data-collapse", "in")
        } else {
            $.getJSON("/comment/" + id, function (json) {

                var gmtCreate = null;
                var commentTime = null;
                var a = Date.now();

                $.each(json.data, function (index, comment) {
                    gmtCreate = comment.gmtCreate;
                    var number = a - gmtCreate;
                    if (number <= 1000 * 60 * 60) {
                        debugger
                        commentTime = moment(gmtCreate).startOf("minute").fromNow();
                    } else if (number <= 1000 * 60 * 60 * 24) {
                        debugger
                        commentTime = moment(gmtCreate).startOf("hour").fromNow();
                    } else {
                        debugger
                        commentTime = moment(gmtCreate).subtract(10, 'days').calendar();
                    }
                    var commentContainer = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm12 col-xs-12 div-border-top",
                    });

                    var media = $("<div/>", {
                        "class": "media",
                    });
                    var mediaLeft = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl,
                    }));
                    var mediaBody = $("<div/>", {
                        "class": "media-body",
                    }).append($("<H4/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.comment
                    })).append($("<div/>", {
                        "class": "menu",
                        "html": commentTime
                    }));
                    media.append(mediaLeft).append(mediaBody);
                    commentContainer.append(media);
                    $("#comment-" + id).append(commentContainer)
                });

                var inputBox = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm12 col-xs-12"
                })

                var commentText = $("<input/>", {
                    "type": "text",
                    "class": "form-control",
                    "placeholder": "评论一下",
                    "id": "input-"+id
                })

                var commentButten = $("<butten/>", {
                    "type": "butten",
                    "class": "btn btn-success",
                    "onclick": "comment(this)",
                    "data-id": id,
                    "html": "评论"
                })
                inputBox.append(commentText);
                inputBox.append(commentButten);
                $("#comment-" + id).append(inputBox);
            });
            //展开二级评论
            comments.addClass("in");
            // e.classList.add()添加class
            //设置二级评论状态
            e.setAttribute("data-collapse", "in")
        }
    }
}


