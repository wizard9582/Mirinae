package com.ecommerce.api;

import com.ecommerce.application.IEthereumService;
import com.ecommerce.domain.Address;
import com.ecommerce.domain.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/eth")
public class EthereumController {

    public static final Logger log = LoggerFactory.getLogger(EthereumController.class);

    private IEthereumService etherService;

    @Autowired
    public EthereumController(IEthereumService etherService) {
        Assert.notNull(etherService, "explorerService 개체가 반드시 필요!");
        this.etherService = etherService;
    }

    /**
     * TODO Sub PJT Ⅲ 추가과제
     * 이더리움 트랜잭션 동기화 데이터로 제공할 수 있는 추가 api의 예 - 주소로 관련 정보 가져오기
     * @param addr
     * @return
     */
    @ApiOperation(value = "Fetch an address info")
    @GetMapping("/address/{addr}")
    public Address getAddress(@PathVariable String addr)
    {
        Address address = this.etherService.getAddress(addr);

        if(address == null)
            throw new NotFoundException(addr + " 주소 정보를 찾을 수 없습니다.");

        return address;
    }
}
