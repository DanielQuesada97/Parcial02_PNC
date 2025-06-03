package com.rockettsttudio.library.config;

import com.rockettsttudio.library.entity.Permission;
import com.rockettsttudio.library.entity.Role;
import com.rockettsttudio.library.entity.User;
import com.rockettsttudio.library.repository.PermissionRepository;
import com.rockettsttudio.library.repository.RoleRepository;
import com.rockettsttudio.library.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Create or get READ_USER permission
        Permission readPermission = permissionRepository.findByName("READ_USER")
                .orElseGet(() -> {
                    Permission permission = new Permission();
                    permission.setName("READ_USER");
                    permission.setDescription("Permission to read user data");
                    return permissionRepository.save(permission);
                });

        // Create or get WRITE_USER permission
        Permission writePermission = permissionRepository.findByName("WRITE_USER")
                .orElseGet(() -> {
                    Permission permission = new Permission();
                    permission.setName("WRITE_USER");
                    permission.setDescription("Permission to write user data");
                    return permissionRepository.save(permission);
                });

        // Create or get admin role
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    role.setDescription("Admin role with all permissions");
                    Set<Permission> permissions = new HashSet<>();
                    permissions.add(readPermission);
                    permissions.add(writePermission);
                    role.setPermissions(permissions);
                    return roleRepository.save(role);
                });

        // Ensure admin role has both permissions
        Set<Permission> adminPermissions = adminRole.getPermissions();
        if (!adminPermissions.contains(readPermission)) {
            adminPermissions.add(readPermission);
        }
        if (!adminPermissions.contains(writePermission)) {
            adminPermissions.add(writePermission);
        }
        adminRole.setPermissions(adminPermissions);
        roleRepository.save(adminRole);

        // Create or update admin user
        User adminUser = userRepository.findByUsername("admin")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("admin");
                    user.setEmail("admin@example.com");
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.setFirstName("Admin");
                    user.setLastName("User");
                    user.setRoles(new HashSet<>());
                    return user;
                });

        // Ensure admin user has the admin role
        Set<Role> userRoles = adminUser.getRoles();
        if (!userRoles.contains(adminRole)) {
            userRoles.add(adminRole);
            adminUser.setRoles(userRoles);
        }
        userRepository.save(adminUser);
    }
} 