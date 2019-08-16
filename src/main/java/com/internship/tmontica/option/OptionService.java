package com.internship.tmontica.option;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionDao optionDao;

    // 옵션 하나 가져오기
    public Option getOptionById(int id){
        return optionDao.getOptionById(id);
    }

    // 옵션 모두 가져오기
    public List<Option> getAllOptions(){
        return optionDao.getAllOptions();
    }

    // 옵션 이름으로 아이디 가져오기
    public int getOptionIdByName(String name){ return optionDao.getOptionIdByName(name);}

}
