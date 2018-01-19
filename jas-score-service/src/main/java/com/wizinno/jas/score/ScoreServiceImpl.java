package com.wizinno.jas.score;
import com.wizinno.jas.score.domain.ScoreMapper;
import com.wizinno.jas.score.domain.model.Score;
import com.wizinno.jas.score.service.ScoreService;
import com.wizinno.jas.score.service.dto.ScoreDto;
import com.wizinno.jas.score.service.dto.UserScoreDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/8/14.
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public List<ScoreDto> getScoreListByUserId(Long userId) {
        List<ScoreDto> scoreDtolist = new ArrayList<>();
        List<Score>scoreList = scoreMapper.getScoreListByUserId(userId);
        ScoreDto scoreDto = null;
        for (Score score:scoreList){
            scoreDto = new ScoreDto();
            BeanUtils.copyProperties(score,scoreDto);
            scoreDtolist.add(scoreDto);
        }
        return scoreDtolist;
    }

    @Override
    public void createScore(ScoreDto scoreDto) {
        Score score = new Score();
        BeanUtils.copyProperties(scoreDto,score);
        scoreMapper.insert(score);

    }

    @Override
    public int updateScore(ScoreDto scoreDto) {
        Score score = new Score();
        BeanUtils.copyProperties(scoreDto,score);
        return scoreMapper.updateByPrimaryKey(score);
    }

    @Override
    public List<UserScoreDto> getScoreList(UserScoreDto userScoreDto) {
        return scoreMapper.getScoreList(userScoreDto);
    }
}
