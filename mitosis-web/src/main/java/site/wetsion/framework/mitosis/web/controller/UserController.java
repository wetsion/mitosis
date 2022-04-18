package site.wetsion.framework.mitosis.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import site.wetsion.framework.mitosis.common.R;

/**
 * @author wetsion
 * @date 4/19/22
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/login")
    public R<JSONObject> login(@RequestBody String body) {
        JSONObject r = new JSONObject();
        r.put("token", "admin-token");
        return R.success(r);
    }

    @GetMapping("/info")
    public R<JSONObject> info(@RequestParam("token") String token) {
        JSONObject r = new JSONObject();
        r.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        r.put("introduction", "I am a super administrator");
        r.put("name", "Wetsion");
        JSONArray roles = new JSONArray();
        roles.add("admin");
        r.put("roles", roles);
        return R.success(r);
    }
}
