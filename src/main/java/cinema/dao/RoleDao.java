 
package cinema.dao;

import cinema.model.Role;
import java.util.Optional;

public interface RoleDao {
    void add(Role role);

    Optional<Role> getRoleByName(Role.RoleName roleName);
}
