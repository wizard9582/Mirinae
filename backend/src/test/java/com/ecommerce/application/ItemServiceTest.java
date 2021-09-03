package com.ecommerce.application;

import com.ecommerce.Application;
import com.ecommerce.domain.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ItemServiceTest
{
	@Autowired
	private IItemService itemService;

	@Transactional
	@Test
	public void testRegister() {
		Item item = new Item();
		item.setName("호롤롤롤로");
		item.setSeller(15);

		Item itemRegistered = this.itemService.register(item);
//		assert itemRegistered.getState().equals("Y");
		assert itemRegistered.getSeller() == 15;
		assert itemRegistered.getName().equals("호롤롤롤로");
		assert itemRegistered.getId() > 0;
	}


	@Test
	public void testList() {
		List<Item> list = this.itemService.list();

		assert list.size() > 0;
	}

	@Test
	public void testGet() {
		Item item = this.itemService.get(1);

		assert item != null;
		assert item.getSeller() == 4;
	}

	@Transactional
	@Test
	public void testUpdate() {

		Item item = this.itemService.get(1);
		item.setName("아아아");
//		item.setState("N");

		this.itemService.update(item);

		Item itemUpdated = this.itemService.get(1);
		assert itemUpdated.getName().equals("아아아");
//		assert itemUpdated.getState().equals("N");
	}

	@Transactional
	@Test
	public void testDelete() {
		long id = 4;
		this.itemService.delete(id);

		Item itemDeleted = this.itemService.get(id);
		assert itemDeleted == null;
	}
}
