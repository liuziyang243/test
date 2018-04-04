package com.crscd.passengerservice.broadcast.content.serviceinterface;

import com.crscd.framework.util.collection.ListUtil;
import com.crscd.framework.util.mapper.MapperUtil;
import com.crscd.passengerservice.broadcast.content.business.MakeReplaceForNormalWildcardInterface;
import com.crscd.passengerservice.broadcast.content.business.MakeReplaceForSpecialWildcardInterface;
import com.crscd.passengerservice.broadcast.content.dao.NormalBroadcastContentDAO;
import com.crscd.passengerservice.broadcast.content.dao.SpecialBroadcastContentDAO;
import com.crscd.passengerservice.broadcast.content.domainobject.*;
import com.crscd.passengerservice.broadcast.content.dto.BroadcastContentSubstitutionDTO;
import com.crscd.passengerservice.broadcast.content.dto.NormalBroadcastContentDTO;
import com.crscd.passengerservice.broadcast.content.dto.SpecialBroadcastContentDTO;
import com.crscd.passengerservice.broadcast.template.enumtype.BroadcastKindEnum;
import com.crscd.passengerservice.result.base.ResultMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuziyang
 * Create date: 2017/9/9
 */
public class BroadcastContentInterfaceImpl implements BroadcastContentInterface {
    private NormalBroadcastContentDAO normalDAO;
    private SpecialBroadcastContentDAO specialDAO;
    private MakeReplaceForNormalWildcardInterface wildcardInterface;
    private MakeReplaceForSpecialWildcardInterface specialWildcardInterface;

    public void setNormalDAO(NormalBroadcastContentDAO normalDAO) {
        this.normalDAO = normalDAO;
    }

    public void setSpecialDAO(SpecialBroadcastContentDAO specialDAO) {
        this.specialDAO = specialDAO;
    }

    public void setWildcardInterface(MakeReplaceForNormalWildcardInterface wildcardInterface) {
        this.wildcardInterface = wildcardInterface;
    }

    public void setSpecialWildcardInterface(MakeReplaceForSpecialWildcardInterface specialWildcardInterface) {
        this.specialWildcardInterface = specialWildcardInterface;
    }

    @Override
    public List<String> getNormalContentNameList(String stationName, BroadcastKindEnum kind) {
        List<NormalBroadcastContent> contents = normalDAO.getContentList(stationName, kind);
        List<String> nameList = new ArrayList<>();
        if (ListUtil.isNotEmpty(contents)) {
            for (NormalBroadcastContent content : contents) {
                nameList.add(content.getContentName());
            }
        }
        return nameList;
    }

    @Override
    public List<BroadcastContentSubstitutionDTO> getNormalContentSubstitutionList() {
        List<NormalBroadcastContentSubstitution> substitutionList = wildcardInterface.getContentSubstitutionList();
        List<BroadcastContentSubstitutionDTO> dtoList = new ArrayList<>();
        for (NormalBroadcastContentSubstitution substitution : substitutionList) {
            dtoList.add(MapperUtil.map(substitution, BroadcastContentSubstitutionDTO.class));
        }
        return dtoList;
    }

    @Override
    public List<NormalBroadcastContentDTO> getNormalContent(String stationName, BroadcastKindEnum kind) {
        List<NormalBroadcastContent> contents = normalDAO.getContentList(stationName, kind);
        List<NormalBroadcastContentDTO> dtoList = new ArrayList<>();
        for (NormalBroadcastContent content : contents) {
            dtoList.add(getNormalDTOFromDO(content));
        }
        return dtoList;
    }

    @Override
    public ResultMessage addNormalContent(NormalBroadcastContentDTO dto) {
        if (normalDAO.insertContent(getNormalDOFromDTO(dto))) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2001);
        }
    }

    @Override
    public ResultMessage modifyNormalContent(NormalBroadcastContentDTO dto) {
        if (normalDAO.modifyContent(getNormalDOFromDTO(dto))) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2002);
        }
    }

    @Override
    public ResultMessage delNormalContent(long id) {
        if (normalDAO.delContent(id)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2003);
        }
    }

    @Override
    public List<String> getSpecialContentKindList(String stationName) {
        return getSpecialKindList(specialDAO.getSpecialContentKindList(stationName));
    }

    @Override
    public ResultMessage addSpecialContentKind(String stationName, String kind) {
        if (specialDAO.insertSpecialContentKind(stationName, kind)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2001);
        }
    }

    @Override
    public ResultMessage delSpecialContentKind(String stationName, String kind) {
        if (specialDAO.delSpecialContentKind(stationName, kind)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2003);
        }
    }

    @Override
    public List<BroadcastContentSubstitutionDTO> getSpecialContentSubstitutionList() {
        List<SpecialBroadcastContentSubstitution> substitutionList = specialWildcardInterface.getContentSubstitutionList();
        List<BroadcastContentSubstitutionDTO> dtoList = new ArrayList<>();
        for (SpecialBroadcastContentSubstitution substitution : substitutionList) {
            dtoList.add(MapperUtil.map(substitution, BroadcastContentSubstitutionDTO.class));
        }
        return dtoList;
    }

    @Override
    public List<SpecialBroadcastContentDTO> getSpecialContent(String stationName, String kind) {
        List<SpecialBroadcastContent> contents = specialDAO.getContentList(stationName, kind);
        List<SpecialBroadcastContentDTO> dtos = new ArrayList<>();
        for (SpecialBroadcastContent content : contents) {
            dtos.add(getSpecialDTOFromDO(content));
        }
        return dtos;
    }

    @Override
    public ResultMessage addSpecialContent(SpecialBroadcastContentDTO dto) {
        if (specialDAO.insertContent(getSpecialDOFromDTO(dto))) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2001);
        }
    }

    @Override
    public ResultMessage modifySpecialContent(SpecialBroadcastContentDTO dto) {
        if (specialDAO.modifyContent(getSpecialDOFromDTO(dto))) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2002);
        }
    }

    @Override
    public ResultMessage delSpecialContent(long id) {
        if (specialDAO.delContent(id)) {
            return new ResultMessage();
        } else {
            return new ResultMessage(2003);
        }
    }

    private NormalBroadcastContentDTO getNormalDTOFromDO(NormalBroadcastContent content) {
        return MapperUtil.map(content, NormalBroadcastContentDTO.class);
    }

    private NormalBroadcastContent getNormalDOFromDTO(NormalBroadcastContentDTO dto) {
        return MapperUtil.map(dto, NormalBroadcastContent.class);
    }

    private SpecialBroadcastContentDTO getSpecialDTOFromDO(SpecialBroadcastContent content) {
        return MapperUtil.map(content, SpecialBroadcastContentDTO.class);
    }

    private SpecialBroadcastContent getSpecialDOFromDTO(SpecialBroadcastContentDTO dto) {
        return MapperUtil.map(dto, SpecialBroadcastContent.class);
    }

    private List<String> getSpecialKindList(List<SpecialBroadcastContentKind> kindList) {
        List<String> stringList = new ArrayList<>();
        for (SpecialBroadcastContentKind kind : kindList) {
            stringList.add(kind.getContentType());
        }
        return stringList;
    }
}
