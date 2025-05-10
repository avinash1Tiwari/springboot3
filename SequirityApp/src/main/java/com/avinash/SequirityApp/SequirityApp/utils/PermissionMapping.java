package com.avinash.SequirityApp.SequirityApp.utils;

import com.avinash.SequirityApp.SequirityApp.enums.Permission;
import com.avinash.SequirityApp.SequirityApp.enums.Permission.*;
import com.avinash.SequirityApp.SequirityApp.enums.Role;
import com.avinash.SequirityApp.SequirityApp.enums.Role.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.avinash.SequirityApp.SequirityApp.enums.Permission.*;
import static com.avinash.SequirityApp.SequirityApp.enums.Role.*;

public class PermissionMapping {

   private  static  final Map<Role, Set<Permission>> map = Map.of(

            USER, Set.of(USER_VEIW,POST_VEIW),
            CREATOR,Set.of(USER_VEIW,POST_VEIW,USER_UPDATE,POST_UPDATE),
            ADMIN, Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE,USER_DELETE,USER_CREATE,POST_DELETE)

    );


   public static Set<SimpleGrantedAuthority> getAuthoritisForRole(Role role)
   {
       return map.get(role).stream().map(
               permission -> new SimpleGrantedAuthority(permission.name()))
               .collect(Collectors.toSet());
   }
}
