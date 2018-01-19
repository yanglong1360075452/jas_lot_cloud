package com.wizinno.jas.score.service;


import com.wizinno.jas.score.service.dto.ScoreDto;
import com.wizinno.jas.score.service.dto.UserScoreDto;

import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */

public interface ScoreService {
   List<ScoreDto> getScoreListByUserId(Long userId);
   void createScore(ScoreDto scoreDto);
   int updateScore(ScoreDto scoreDto);
   List<UserScoreDto> getScoreList(UserScoreDto userScoreDto);
}
