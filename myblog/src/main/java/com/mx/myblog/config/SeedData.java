package com.mx.myblog.config;

import com.mx.myblog.models.Account;
import com.mx.myblog.models.Authority;
import com.mx.myblog.models.Post;
import com.mx.myblog.repositories.AuthorityRepository;
import com.mx.myblog.services.AccountService;
import com.mx.myblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.size() == 0) {

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);
            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);


            Account account1 = new Account();
            Account account2 = new Account();

            account1.setEmail("test@email.com");
            account1.setPassword("password");
            account1.setFirstname("Test");
            account1.setLastname("Test");
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);

            account2.setEmail("admin@email.com");
            account2.setPassword("password");
            account2.setFirstname("admin");
            account2.setLastname("admin");
            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);

            accountService.save(account1);
            accountService.save(account2);

            Post post1 = new Post();
            post1.setTitle("title1");
            post1.setBody("body1");
            post1.setCreatedAt(LocalDateTime.now());
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("title2");
            post2.setBody("body2");
            post2.setCreatedAt(LocalDateTime.now());
            post2.setAccount(account1);

            Post post3 = new Post();
            post3.setTitle("title3");
            post3.setBody("body3");
            post3.setCreatedAt(LocalDateTime.now());
            post3.setAccount(account2);

            postService.save(post1);
            postService.save(post2);
            postService.save(post3);
        }
    }
}
