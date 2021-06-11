//提交问题回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment").val();
    commentToTargetId(questionId,1,content);
}
//提交评论回复
function commmentToComment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    commentToTargetId(commentId,2,content);


}
function commentToTargetId(targetId,type,content){
    if(!content){
        alert("回復不能為空");
        return;
    }
    $.ajax({
        type:"post",
        url:"/comment",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        contentType:"application/json",
        success:function(response){
            if(response.code == 200){
                // $("#comment_section").hide();
                window.location.reload();
            }else{
                if(response.code == 2003){
                    if(confirm(response.message())){
                        window.open("https://github.com/login/oauth/authorize?client_id=6e4a950d6e17acbf4d62&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(response.message);
                }

            }
            console.log(response);
        },
        dataType:"json"
    });

}
//展开二级评论
function collapseComments(e) {

    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    comments.toggleClass("in");
    if(comments.hasClass("in")){
        if(comments.children().length == 1){
            $.getJSON("/comment/"+id,function (data) {
                var commentBody = $("comment-body-"+id);
                $.each(data.data.reverse(), function(index,comment){
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    var mediaBody = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h6/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    }).append($("<span/>",{
                        "class":"span",
                        "html":moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));
                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBody);
                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments ",
                    }).append(mediaElement);
                    comments.prepend(commentElement);
                });
            })
        }
        //点亮

    }else{
        //熄灭
        e.classList.remove("active");
    }

}
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $('#tag').val();
    if(previous.indexOf(value) == null){
        return;
    }
    if(previous.indexOf(value) == -1){
        if(previous){
            $('#tag').val(previous + ',' + value);
        }else{
            $('#tag').val(value);
        }
    }

}
function showSelectTag() {
    $('#select-tag').show();

}