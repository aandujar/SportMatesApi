package com.sportMates.Service;

import com.sportMates.Entities.Sport;

import java.util.List;

public interface SportService {

    List<Sport> getAllSports();

    Sport getSportByCode(String sportCode);
}
