package service;

import po.TbAttence;

import java.util.List;

public interface AttenceService {
    List<TbAttence> getMyAttence(int userId, int firstRow, int rowCount);

    int getMyAttenceCount(int userId);

    void addAttence(List<TbAttence> attenceList);

    List<TbAttence> getAllAttence(int firstRow, int rowCount);

    int getAllAttenceCount();
}
