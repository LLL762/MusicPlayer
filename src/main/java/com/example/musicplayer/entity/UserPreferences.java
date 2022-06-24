package com.example.musicplayer.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPreferences {

	@JacksonXmlElementWrapper(localName = "path")
	private String favoritePath;
	private String lastPath;

}
