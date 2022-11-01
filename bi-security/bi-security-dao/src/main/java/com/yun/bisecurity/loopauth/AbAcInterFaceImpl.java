package com.yun.bisecurity.loopauth;

import com.sobercoding.loopauth.abac.face.AbacInterface;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.yun.bisecurity.entity.AbacPolicyEntity;
import com.yun.bisecurity.dto.AbacPolicyQueryDto;
import com.yun.bisecurity.service.AbacPolicyService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sober
 */
@Component
public class AbAcInterFaceImpl implements AbacInterface {

    @Resource
    private AbacPolicyService abacPolicyService;

    /**
     * 获取一个或多个路由/权限代码所属的 规则
     *
     * @param route            路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode) {
        // 组建查询参数
        AbacPolicyQueryDto queryParam = new AbacPolicyQueryDto();
        queryParam.setRoute(route);
        queryParam.setMode(loopAuthHttpMode.name());
        // 获取相关规则
        List<AbacPolicyEntity> abacPolicyEntityList = abacPolicyService
                .queryList(queryParam);
        // 组装数据
        return abacPolicyEntityList.stream()
                .map(AbacPolicyEntity.abacPolicyToPolicy)
                .collect(Collectors.toSet());
    }
}