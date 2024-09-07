package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.PetService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = mapPetDTOToPet(petDTO);
        Pet createdPet = petService.savePet(pet, petDTO.getOwnerId());
        return mapPetToPetDTO(createdPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        PetDTO petDTO = mapPetToPetDTO(pet);
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOs = mapPetsToDTOs(pets);
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByCustomerId(ownerId);
        List<PetDTO> petDTOs = mapPetsToDTOs(pets);
        return petDTOs;
    }

    // map entity to DTO
    private PetDTO mapPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        
        return petDTO;
    }

    // map DTO to entity
    private Pet mapPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
    // map pets to PetDTOs
    private List<PetDTO> mapPetsToDTOs(List<Pet> pets) {
        List<PetDTO> petDTOs = new ArrayList<>();
        pets.forEach(pet -> petDTOs.add(mapPetToPetDTO(pet)));
        return petDTOs;
    }
}
