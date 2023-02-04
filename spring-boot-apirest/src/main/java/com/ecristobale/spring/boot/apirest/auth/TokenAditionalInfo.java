package com.ecristobale.spring.boot.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.ecristobale.spring.boot.apirest.models.entity.User;
import com.ecristobale.spring.boot.apirest.models.services.IUserService;

@Component
public class TokenAditionalInfo implements TokenEnhancer {

	@Autowired
    IUserService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = usuarioService.findByUsername(authentication.getName());
		
		Map<String, Object> info = new HashMap<>();
		info.put("aditional_info", "user: " + authentication.getName());
		info.put("user", user.getId() + ": " + user.getUsername());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
