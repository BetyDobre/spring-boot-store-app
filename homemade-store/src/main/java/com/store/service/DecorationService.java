package com.store.service;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;
import com.store.mapper.DecorationMapper;
import com.store.repository.DecorationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DecorationService {

    private final DecorationRepository decorationRepository;
    private final DecorationMapper decorationMapper;

    public DecorationService(DecorationRepository decorationRepository, DecorationMapper decorationMapper) {
        this.decorationRepository = decorationRepository;
        this.decorationMapper = decorationMapper;
    }

    public Decoration findDecorationByDecorationId(Long decorationId){
        return decorationRepository.findDecorationByDecorationId(decorationId);
    }

    public DecorationDto add(DecorationDto decorationDto, String category) {
        Decoration decoration = decorationMapper.mapToEntity(decorationDto);
        if(category.equalsIgnoreCase("CHRISTMAS")){
            decoration.setCategory(DecorationCategory.CHRISTMAS);
        }
        else if(category.equalsIgnoreCase("EASTER")){
            decoration.setCategory(DecorationCategory.EASTER);
        }
        else if(category.equalsIgnoreCase("MARCH")){
            decoration.setCategory(DecorationCategory.MARCH);
        }
        Decoration savedDecoration = decorationRepository.save(decoration);
        return  decorationMapper.mapToDto(savedDecoration);
    }

    public DecorationDto getOne(Long id) {
        return decorationMapper.mapToDto(decorationRepository.findDecorationByDecorationId(id));
    }

    public List<DecorationDto> getAllDecorations() {
        List<Decoration> decorations = decorationRepository.findAll();

        return decorations.stream().map(d -> decorationMapper.mapToDto(d)).collect(Collectors.toList());
    }

    public List<DecorationDto> getByCategory(DecorationCategory category) {

        List<Decoration> decorations = decorationRepository.findAllByCategory(category);

        return decorations.stream().map(d -> decorationMapper.mapToDto(d)).collect(Collectors.toList());
    }

    public DecorationDto update(DecorationDto decorationDto, Long id) {
        Decoration decoration = decorationRepository.findDecorationByDecorationId(id);
        Decoration updatedDecoration = decorationMapper.mapToEntity(decorationDto);

        if(updatedDecoration.getDecorationName() != null){
            decoration.setDecorationName(updatedDecoration.getDecorationName());
        }
        if(updatedDecoration.getCategory() != null){
            decoration.setCategory(updatedDecoration.getCategory());
        }
        if(updatedDecoration.getDescription() != null){
            decoration.setDescription(updatedDecoration.getDescription());
        }
        if(updatedDecoration.getPrice() != 0){
            decoration.setPrice(updatedDecoration.getPrice());
        }

        Decoration savedDecoration = decorationRepository.save(decoration);
        return decorationMapper.mapToDto(savedDecoration);
    }
}
