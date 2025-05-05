package org.example.avb_test.company_service.mapper;

import org.example.avb_test.company_service.dto.CompanyCreateDto;
import org.example.avb_test.company_service.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.example.common.dto.CompanyResponceDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CompanyMapper {
    Company toEntity(CompanyCreateDto companyCreateDto);

    CompanyResponceDto toDto(Company company);

    List<CompanyResponceDto> toDtoList(List<Company> companies);

    List<Company> toEntityList(List<CompanyCreateDto> companyCreateDtos);

    void update(CompanyCreateDto companyCreateDto, @MappingTarget Company company);
}
