package pl.szotaa.snippr.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.szotaa.snippr.snippet.service.SnippetService;
import pl.szotaa.snippr.user.service.ApplicationUserService;

@Component
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class SecurityExpressions {

    private final SnippetService snippetService;
    private final ApplicationUserService applicationUserService;

    public boolean isSnippetOwner(Long snippetId, Authentication authentication){
        if(snippetService.getById(snippetId).getOwner().getUsername().equals(authentication.getName())){
            return true;
        }
        return false;
    }

    public boolean isHimself(Long applicationUserId, Authentication authentication){
        if(applicationUserService.getById(applicationUserId).getUsername().equals(authentication.getName())){
            return true;
        }
        return false;
    }
}
