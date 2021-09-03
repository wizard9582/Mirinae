package com.ecommerce.application;

import com.ecommerce.Application;
import com.ecommerce.domain.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EthereumServiceTest
{
	@Autowired
	private IEthereumService ethereumService;

	@Test
	public void testRequestEth()
	{
		String toAddress = "0x6190E280834C9a3414EC2b93B268b629206ab65C";
		String txhash = this.ethereumService.requestEth(toAddress);

		System.out.println(txhash);
		assert txhash != null || !txhash.equals("");
	}

	@Test
	public void testGetAddress()
	{
		String addressToBeFetched = "0x66eDaFE1d6073Fb3bB8DD6eCFEE95319FEb2787D";
		Address addr = this.ethereumService.getAddress(addressToBeFetched);

		assert addr != null;
	}
}