package service.impl;

import com.github.pagehelper.PageHelper;
import mapper.TbAttenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import po.TbAttence;
import service.AttenceService;

import java.util.List;

@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout=5)
@Service
public class AttenceServiceImpl implements AttenceService {
    @Autowired
    TbAttenceMapper attenceMapper;

    @Override
    public List<TbAttence> getMyAttence(int userId, int firstRow, int rowCount) {
        //PageHelper.startPage(firstRow,rowCount);
        return attenceMapper.getMyAttence(userId);
    }

    @Override
    public int getMyAttenceCount(int userId) {
        return attenceMapper.getMyAttenceCount(userId);
    }
}
