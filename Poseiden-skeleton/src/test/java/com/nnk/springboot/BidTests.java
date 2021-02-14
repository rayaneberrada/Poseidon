package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BidTests {

  @Autowired BidListRepository bidListRepository;

  @Test
  public void bidListTest() {
    BidList bid = new BidList();
    bid.setAccount("Account Test");
    bid.setType("Type Test");
    bid.setBidQuantity(10d);

    // Save
    bid = bidListRepository.save(bid);
    Assert.assertNotNull(bid.getBidListId());
    Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

    // Update
    bid.setBidQuantity(20d);
    bid = bidListRepository.save(bid);
    Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

    // Find
    List<BidList> listResult = bidListRepository.findAll();
    Assert.assertTrue(listResult.size() > 0);

    // Delete
    Integer id = bid.getBidListId();
    bidListRepository.delete(bid);
    Optional<BidList> bidList = bidListRepository.findById(id);
    Assert.assertFalse(bidList.isPresent());
  }
}
