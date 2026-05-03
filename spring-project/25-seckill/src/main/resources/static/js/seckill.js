var secKillObj={
    contextPath :"",
    url:{
        getSystemTime:function(){
            return "/getSystemTime";
        },
       doSecKill:function(goodsId,startTime,endTime,randomName){
            return "/doSecKill/"+goodsId+"/"+startTime+"/"+endTime+"/"+randomName;
        },
        getOrderResult :function(randomName){
            return "/getOrderResult/"+randomName;
        }
    },
    fun:{
        initDetail:function( goodId, startTime, endTime,randomName){
            $.ajax({
                url:secKillObj.url.getSystemTime(),
                type:"get",
                dataType:"json",
                success:function(data){
                    if(data.data<startTime){
                       secKillObj.fun.secKillCountdown(startTime);
                        return false;
                    }
                    if(data.data>endTime){
                        $("#seckillBox").html("秒杀已经结束");
                        return false;
                    }
                    secKillObj.fun.secKill(goodId,startTime,endTime,randomName);
                },
                error:function(){
                    alert("网络错误！")
                }
            });
        },
        secKillCountdown:function(startTime){
            $("#seckillBox").countdown(startTime,function(event){
                    $("#seckillBox").html(event.strftime("距离秒杀还有: %D天 %H小时 %M分钟 %S秒" ))
            })
        },
        secKill:function(goodsId,startTime,endTime,randomName){
            if(randomName==""||randomName=="null"||randomName== null){
                $("#seckillBox").html("当前商品不可秒杀");
                return false;
            }
            $("#seckillBox").html("<input type='button' value='秒杀' id='secKillBut'>")
            $("#secKillBut").bind("click",function(){
                $(this).prop("disabled",true)
                $.ajax({
                    url:secKillObj.url.doSecKill(goodsId,startTime,endTime,randomName),
                    type:"get",
                    dataType:"json",
                    success:function(data){
                        if(data.errorCode=='1'){
                            alert(data.errorMessage)
                            return false;
                        }
                       secKillObj.fun.getOrderResult(randomName);

                    },
                    error:function(){
                        alert("-----网络错误!")
                    }
                })
            })
        },
        getOrderResult :function(randomName){

            $.ajax({
                url:secKillObj.url.getOrderResult(randomName),
                dataType:"json",
                type:"get",
                success:function(data){
                    if(data.errorCode=='1'){
                        window.setTimeout("secKillObj.fun.getOrderResult(randomName)",3000);
                        return ;
                    }
                    $("#seckillBox").html("下单成功，请在45分钟内完成支付:共"+data.data.orderMoney+"元<br> <a href='xxxxxxx'>立即支付</a>");
                }
            })

        }

    }
}