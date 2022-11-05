package top.moflowerlkh.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.moflowerlkh.web.dto.RequestDTO;
import top.moflowerlkh.web.service.OrderCommandService;
import top.moflowerlkh.web.vo.UIResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "商品订单")
@RestController
@Valid
public class GoodsOrderController {
    @Autowired
    OrderCommandService orderCommandService;

    @ApiOperation("秒杀下单")
    @PostMapping(value = "/order/{random}")
    public UIResult GoodsOrder(
            HttpServletRequest req,
            @RequestBody RequestDTO requestDTO,
            @PathVariable("random") String randomPath) {
        String result = orderCommandService.goodsOrderCommand(requestDTO);
        return UIResult.Success(result, requestDTO.getRequestID());
    }

    @ApiOperation("查询秒杀结果")
    @PostMapping("/order-result")
    public UIResult goodsOrderResult(@RequestBody RequestDTO requestDTO) {
        return UIResult.Success("OK", orderCommandService.getResponse(requestDTO));
    }

}
