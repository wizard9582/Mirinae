package com.ecommerce.domain.repository;

import com.ecommerce.domain.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IItemRepository
{
	List<Item> list();
	List<Item> getByUser(final long userId);
	Item get(long id);

	@Transactional
	long create(Item item);

	@Transactional
	int update(Item item);

	@Transactional
	int delete(long id);
}
