//提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    cpmment2target(questionId,1,content);
}

function comment(e) {
    var commentId = e.getAttribute("data-target");
    var content=$("#input-" + commentId).val();
    cpmment2target(commentId,2,content);
}


function cpmment2target(targetId,type,content) {

    var message_send = {
        "parentId":targetId,
        "content":content,
        "type":type
    }

    if(!content){
        alert("不能回复空内容~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data:JSON.stringify(message_send), //将对象转为为json字符串
        success: function (response) {
            if(response.code == 200){
                window.location.reload()
                $("#comment_section").hide();
            }else{
                if(response.code == 2003){
                    var isAccepted =  confirm(response.message);
                    if(isAccepted){
                        window.open("/login");
                        window.localStorage.setItem("closeable",true);
                    }
                }else {
                    alert(response.message);
                }

            }
            console.log(response);

        },
        dataType: "json"
    });


}







// 展开二级评论
function collapseComments(e) {

    var target = e.getAttribute("data-target");
    var comments=$("#comment-" + target);




    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("onclick");
    }
    else {
        var subCommentContainer = $('#comment-'+target);

        if(subCommentContainer.children().length != 1){
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse","in");
            e.classList.add("onclick");
        }else{
            $.getJSON( "/comment/"+ target , function( data ) {
                console.log(data);
                var items = [];
                $.each( data.data.reverse(), function(index , comment) {


                    var mediaBodyElement  = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading comment-list-body",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                            "class":"comment-menu"
                        }).append( $("<span/>",{
                            "class":"pull-right",
                            "html":moment(comment.gmtCreate).format('YYYY/MM/DD')
                        }))
                    );


                    var mediaLeftElement  = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object user_picture img-rounded",
                        "src":comment.user.avatarUrl
                    }));


                    var mediaElement  = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);


                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 clo-md-12 clo-sm-12 clo-xs-12 comment-second"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse","in");
                e.classList.add("onclick");
            });
        }






    }




}