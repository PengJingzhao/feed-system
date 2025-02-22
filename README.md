# feed-system
基于时间线的信息推送系统

### **Sprint 1 具体计划**

#### **Sprint 1 目标**

- 搭建项目基础框架，完成微服务的骨架设计。
- 实现用户动态发布功能（Item服务）。
- 实现用户关注/取消关注功能（Relation服务）。
- 搭建开发环境，适配分布式服务和消息队列。

------

### **Sprint 1 时间安排**

- **周期**：2周（10个工作日）

- 团队分工

  ：

  - **后端开发**：搭建骨架、服务实现、数据库设计。
  - **测试工程师**：编写单元测试用例，验证功能逻辑。
  - **架构师**：确保服务的架构设计合理，指导团队开发。
  - **DevOps**：搭建开发环境、配置CI/CD。

------

### **Sprint 1 任务分解**

#### **1. 项目初始化**

- **时间**：第1~2天

- 任务

  ：

  1. 搭建SpringBoot微服务框架

     ：

     - 创建5个服务模块：`Gateway`、`Feed`、`Item`、`Relation`、`Count`。
     - 引入必要的依赖，包括`Dubbo`、`SpringBoot`、`MySQL`、`Kafka`、`Redis`。

  2. 注册中心设置

     ：

     - 使用`Zookeeper`或`Nacos`作为服务注册中心，配置服务注册和发现。

  3. 开发环境搭建

     ：

     - 配置本地运行环境（Docker Compose 运行 MySQL、Redis、Kafka、MinIO）。
     - 确保本地可以运行每个服务，并进行简单的 Dubbo 服务调用测试。

  4. 代码仓库初始化

     ：

     - 创建 Git 仓库，设置分支管理策略（如主分支 `main`，开发分支 `develop`）。

  5. CI/CD 配置

     ：

     - 设置代码提交后自动化构建（Jenkins 或 GitHub Actions）。

------

#### **2. Item服务：动态发布**

- **时间**：第3~6天

- **目标**：用户可以发布动态，支持文本、图片、视频。

- 任务

  ：

  1. 动态表设计

     ：

     - MySQL 数据库设计：

       - 动态表

          

         ```
         item
         ```

         ：

         ```sql
         CREATE TABLE item (
             id BIGINT PRIMARY KEY AUTO_INCREMENT,
             user_id BIGINT NOT NULL,          -- 发布动态的用户ID
             content TEXT,                    -- 动态内容
             media_urls VARCHAR(255),         -- 多媒体文件URL（图片/视频）
             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 动态发布时间
             INDEX idx_user_id (user_id)      -- 索引用于快速查询用户动态
         );
         ```

       - 考虑后期分库分表的扩展性。

  2. 动态发布接口

     ：

     - 发布动态接口：

       - HTTP POST `/item/publish`

       - 请求参数：

         ```json
         {
           "userId": 12345,
           "content": "Hello world!",
           "mediaUrls": ["http://example.com/image1.jpg", "http://example.com/video1.mp4"]
         }
         ```

       - 返回结果：

         ```json
         {
           "code": 200,
           "message": "Success",
           "data": {
             "itemId": 789
           }
         }
         ```

  3. 集成 MinIO 文件存储

     ：

     - 配置 MinIO，支持多媒体文件上传。
     - 上传接口：
       - HTTP POST `/item/upload`
       - 响应返回多媒体文件的 URL。

  4. 测试

     ：

     - 编写单元测试，验证动态发布功能。

------

#### **3. Relation服务：关注/取消关注**

- **时间**：第3~6天（并行开发）

- **目标**：用户之间可以关注和取消关注。

- 任务

  ：

  1. 用户关系表设计

     ：

     - Redis 作为主要存储：

       - 用户关注关系键：`user:follow:{userId}`（存储关注的用户ID集合）。
       - 用户粉丝关系键：`user:fans:{userId}`（存储粉丝的用户ID集合）。

     - MySQL 作为持久化存储：

       ```sql
       CREATE TABLE user_relation (
           id BIGINT PRIMARY KEY AUTO_INCREMENT,
           user_id BIGINT NOT NULL,          -- 用户ID
           follow_id BIGINT NOT NULL,        -- 被关注的用户ID
           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
           INDEX idx_user_follow (user_id, follow_id)
       );
       ```

  2. 关注/取消关注接口

     ：

     - 关注接口：

       - HTTP POST `/relation/follow`

       - 请求参数：

         ```json
         {
           "userId": 12345,
           "followId": 67890
         }
         ```

       - 响应结果：

         ```json
         {
           "code": 200,
           "message": "Follow success"
         }
         ```

     - 取消关注接口：

       - HTTP POST `/relation/unfollow`
       - 请求参数同上。

  3. 测试

     ：

     - 使用 Mock 数据，验证 Redis 和 MySQL 的操作逻辑。

------

#### **4. 联调与整合**

- **时间**：第7~8天

- 任务

  ：

  1. Item服务和Relation服务联调

     ：

     - 发布动态的用户是否需要先校验关注状态。
     - 如果有交互，Relation服务返回用户的关注列表。

  2. 编写接口文档

     ：

     - 使用 Swagger 或 Postman 定义接口，方便后续测试和对接。

  3. 测试服务间调用

     ：

     - 使用 Dubbo 测试 RPC 调用的性能是否满足需求。

------

#### **5. 测试与评审**

- **时间**：第9~10天

- 任务

  ：

  1. 单元测试

     ：

     - 对 Item 和 Relation 服务的每个接口进行单独测试。

  2. 集成测试

     ：

     - 测试服务之间的数据交互是否完整（如动态发布后能在数据库中查到）。

  3. Bug修复

     ：

     - 修复发现的功能性和逻辑性 Bug。

  4. Sprint评审

     ：

     - 演示已完成功能：
       - 用户可以发布动态。
       - 用户可以关注其他用户。
       - 动态关联正确的用户关系。
     - 收集反馈，为下一次迭代做准备。

------

### **Sprint 1 交付成果**

1. 代码交付

   ：

   - 完成 Item 和 Relation 服务的代码开发，推送到Git主分支。

2. 接口文档

   ：

   - Swagger 或 Postman 文档，描述发布动态和关注相关接口。

3. 数据库与服务运行

   ：

   - 搭建完成基础环境，支持服务正常运行。

4. 初步测试报告

   ：

   - 动态发布、关注功能均通过单元测试和集成测试。

------

### **Sprint 1 总结**

Sprint 1 的核心目标是完成项目的基础搭建和关键功能的实现。通过这一阶段，我们实现了以下：

- 微服务框架搭建。
- 动态发布功能。
- 用户关系功能。 这些是后续实现 Feed 流和互动功能的基础，为接下来的 Sprint 提供了稳定的技术架构和开发环境。

### **Item 服务设计**

`Item 服务` 是负责处理动态（帖子/内容）发布和管理的核心服务。它的主要职责包括动态的创建、查询、更新、删除等操作，以及文件（图片/视频）上传的支持。下面是基于实际需求设计的详细内容。

------

## **核心功能及接口设计**

### **主要功能**

1. 动态的发布

   ：

   - 用户可以发布带文本、图片或视频的动态。

2. 动态的查询

   ：

   - 支持查询单个动态。
   - 获取指定用户发布的动态列表。

3. 动态的删除

   ：

   - 用户可以删除自己发布的动态。

4. 多媒体文件上传

   ：

   - 支持图片和视频的上传，保存到 MinIO 或其他对象存储服务。

5. 动态状态的修改

   （可扩展）：

   - 修改动态内容（如更新文本或媒体 URL）。

6. 批量操作支持

   （可扩展）：

   - 批量查询动态。

------

### **接口设计**

#### **1. 动态发布接口**

- **功能**：用户发布动态，支持文本、图片、视频。

- **请求方法**：`POST`

- **接口路径**：`/item/publish`

- 请求参数

  ：

  ```json
  {
    "userId": 12345,
    "content": "This is a new post!",
    "mediaUrls": [
      "http://example.com/image1.jpg",
      "http://example.com/video1.mp4"
    ]
  }
  ```

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Success",
    "data": {
      "itemId": 789
    }
  }
  ```

- 说明

  ：

  - `userId`：发布动态的用户 ID。
  - `content`：动态的文本内容。
  - `mediaUrls`：动态关联的多媒体文件 URL（图片或视频）。

------

#### **2. 查询单个动态**

- **功能**：根据动态 ID 查询动态详情。

- **请求方法**：`GET`

- **接口路径**：`/item/{itemId}`

- **请求参数**：路径参数 `itemId`（动态 ID）。

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Success",
    "data": {
      "itemId": 789,
      "userId": 12345,
      "content": "This is a new post!",
      "mediaUrls": [
        "http://example.com/image1.jpg",
        "http://example.com/video1.mp4"
      ],
      "createdAt": "2025-02-17 12:00:00"
    }
  }
  ```

------

#### **3. 查询用户动态列表**

- **功能**：查询某个用户发布的动态列表，支持分页。

- **请求方法**：`GET`

- **接口路径**：`/item/user/{userId}`

- 请求参数

  ：

  - Path 参数：
    - `userId`：用户 ID。
  - Query 参数：
    - `page`：页码（默认 1）。
    - `size`：每页条数（默认 10）。

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Success",
    "data": {
      "items": [
        {
          "itemId": 789,
          "content": "This is a new post!",
          "mediaUrls": [
            "http://example.com/image1.jpg"
          ],
          "createdAt": "2025-02-17 12:00:00"
        },
        {
          "itemId": 790,
          "content": "Another post",
          "mediaUrls": [],
          "createdAt": "2025-02-16 15:30:00"
        }
      ],
      "page": 1,
      "size": 2,
      "total": 50
    }
  }
  ```

------

#### **4. 动态删除**

- **功能**：删除指定动态。

- **请求方法**：`DELETE`

- **接口路径**：`/item/{itemId}`

- **请求参数**：路径参数 `itemId`（动态 ID）。

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Item deleted successfully"
  }
  ```

- 说明

  ：

  - 动态只能由发布者自己删除。

------

#### **5. 动态更新**

- **功能**：修改动态文本内容或媒体 URL。

- **请求方法**：`PUT`

- **接口路径**：`/item/{itemId}`

- 请求参数

  ：

  ```json
  {
    "content": "Updated content!",
    "mediaUrls": [
      "http://example.com/new_image.jpg"
    ]
  }
  ```

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Item updated successfully"
  }
  ```

------

#### **6. 文件上传接口**

- **功能**：用户上传图片或视频文件，返回存储后的 URL。

- **请求方法**：`POST`

- **接口路径**：`/item/upload`

- 请求参数

  ：

  - Multipart 文件上传，表单字段名为 `file`。

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "File uploaded successfully",
    "data": {
      "url": "http://example.com/uploads/image1.jpg"
    }
  }
  ```

------

#### **7. 批量查询动态接口**

- **功能**：根据动态 ID 批量查询动态详情。

- **请求方法**：`POST`

- **接口路径**：`/item/batch`

- 请求参数

  ：

  ```json
  {
    "itemIds": [789, 790, 791]
  }
  ```

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Success",
    "data": [
      {
        "itemId": 789,
        "content": "This is a new post!",
        "mediaUrls": [
          "http://example.com/image1.jpg"
        ],
        "createdAt": "2025-02-17 12:00:00"
      },
      {
        "itemId": 790,
        "content": "Another post",
        "mediaUrls": [],
        "createdAt": "2025-02-16 15:30:00"
      }
    ]
  }
  ```

------

## **数据库设计**

### **动态表 (item)**

```sql
CREATE TABLE item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '动态 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    content TEXT COMMENT '动态内容',
    media_urls TEXT COMMENT '多媒体文件 URL (JSON 格式)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '动态发布时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '动态更新时间',
    INDEX idx_user_id (user_id), -- 按用户查询动态
    INDEX idx_created_at (created_at) -- 按时间排序查询
);
```

------

## **服务实现方案**

### **1. 核心逻辑**

- **动态数据存储**：动态的元信息保存在 MySQL 数据库，媒体文件存储在 MinIO。
- **缓存引入**：通过 Redis 缓存热门动态，提升查询性能。

### **2. 异步处理**

- 使用 Kafka 实现异步的动态处理，比如动态发布后通知 Feed 服务推送给粉丝。

### **3. 安全性**

- 在删除和更新动态时，校验用户是否有权限操作。

------

## **总结**

`Item 服务` 的设计注重：

1. **基础功能**：如发布、查询、删除等动态操作。
2. **存储设计**：动态元信息和多媒体文件分开存储。
3. **性能优化**：通过分页、缓存和批量接口优化查询性能。
4. **扩展性**：支持后续动态的互动功能（如点赞、评论）。

这套设计能满足动态发布和管理系统的核心需求，同时支持后续的扩展。

## **Relation 服务功能描述**

### **核心功能**

1. **关注用户**：用户可以关注其他用户。
2. **取消关注**：用户可以取消对其他用户的关注。
3. **获取关注列表**：获取某用户的关注用户列表，支持分页。
4. **获取粉丝列表**：获取某用户的粉丝列表，支持分页。
5. **关系查询**：查询两个用户之间是否存在关注关系。

------

## **接口设计**

### **1. 关注用户**

- **功能**：添加关注关系。

- **请求方法**：`POST`

- **接口路径**：`/relation/follow`

- 请求参数

  ：

  ```
  {
    "userId": 12345,      // 发起关注操作的用户
    "followId": 67890     // 被关注的用户
  }
  ```

- 响应结果

  ：

  ```
  {
    "code": 200,
    "message": "Follow success"
  }
  ```

- 说明

  ：

  - 若用户已关注，返回成功但不重复插入数据。

------

### **2. 取消关注**

- **功能**：删除关注关系。

- **请求方法**：`POST`

- **接口路径**：`/relation/unfollow`

- 请求参数

  ：

  ```
  {
    "userId": 12345,      // 发起取消操作的用户
    "followId": 67890     // 被取消关注的用户
  }
  ```

- 响应结果

  ：

  ```
  {
    "code": 200,
    "message": "Unfollow success"
  }
  ```

- 说明

  ：

  - 若关注关系不存在，返回成功但无实际操作。

------

### **3. 获取关注列表**

- **功能**：获取指定用户的关注列表（支持分页）。

- **请求方法**：`GET`

- **接口路径**：`/relation/following/{userId}`

- 请求参数

  ：

  - Path 参数：
    - `userId`：用户 ID。
  - Query 参数：
    - `page`：页码（默认 1）。
    - `size`：每页记录数（默认 10）。

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Success",
    "data": {
      "userId": 12345,
      "following": [
        {
          "id": 67890,
          "username": "user67890",
          "avatar": "http://example.com/avatar.jpg"
        },
        {
          "id": 67891,
          "username": "user67891",
          "avatar": "http://example.com/avatar2.jpg"
        }
      ],
      "page": 1,
      "size": 2,
      "total": 20
    }
  }
  ```

------

### **4. 获取粉丝列表**

- **功能**：获取指定用户的粉丝列表（支持分页）。

- **请求方法**：`GET`

- **接口路径**：`/relation/followers/{userId}`

- 请求参数

  ：

  - Path 参数：
    - `userId`：用户 ID。
  - Query 参数：
    - `page`：页码（默认 1）。
    - `size`：每页记录数（默认 10）。

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Success",
    "data": {
      "userId": 12345,
      "followers": [
        {
          "id": 67890,
          "username": "user67890",
          "avatar": "http://example.com/avatar.jpg"
        },
        {
          "id": 67891,
          "username": "user67891",
          "avatar": "http://example.com/avatar2.jpg"
        }
      ],
      "page": 1,
      "size": 2,
      "total": 15
    }
  }
  ```

------

### **5. 查询关注关系**

- **功能**：判断两个用户之间是否存在关注关系。

- **请求方法**：`GET`

- **接口路径**：`/relation/check`

- 请求参数

  ：

  - Query 参数：
    - `userId`: 用户 ID。
    - `followId`: 被检查的用户 ID。

- 响应结果

  ：

  ```json
  {
    "code": 200,
    "message": "Success",
    "data": {
      "isFollowing": true
    }
  }
  ```

------

## **数据库设计**

### **1. 用户关系表**

关系表存储用户的关注关系，支持快速查询。

```sql
CREATE TABLE user_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    follow_id BIGINT NOT NULL COMMENT '被关注用户 ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
    INDEX idx_user_follow (user_id, follow_id), -- 用户关注关系索引
    INDEX idx_follow_user (follow_id, user_id)  -- 粉丝查找索引
);
```

### **2. 缓存设计（Redis）**

为提高查询性能，可以将部分关系数据存储在 Redis 中：

1. 用户关注列表

   ：

   - Key：`relation:following:{userId}`
   - Value：用户关注的用户集合。

2. 用户粉丝列表

   ：

   - Key：`relation:followers:{userId}`
   - Value：用户粉丝的用户集合。

------

## **服务方法设计**

### **1. 关注用户**

```json
public boolean followUser(Long userId, Long followId) {
    // 检查是否已经关注
    if (relationRepository.existsByUserIdAndFollowId(userId, followId)) {
        return false;
    }
    
    // 保存到数据库
    userRelationRepository.save(new UserRelation(userId, followId));

    // 更新 Redis 缓存
    redisTemplate.opsForSet().add("relation:following:" + userId, followId);
    redisTemplate.opsForSet().add("relation:followers:" + followId, userId);

    return true;
}
```

------

### **2. 取消关注**

```java
public boolean unfollowUser(Long userId, Long followId) {
    // 检查是否存在关注关系
    if (!relationRepository.existsByUserIdAndFollowId(userId, followId)) {
        return false;
    }

    // 删除关系
    relationRepository.deleteByUserIdAndFollowId(userId, followId);

    // 更新 Redis 缓存
    redisTemplate.opsForSet().remove("relation:following:" + userId, followId);
    redisTemplate.opsForSet().remove("relation:followers:" + followId, userId);

    return true;
}
```

------

### **3. 获取关注列表**

```
public List<Long> getFollowingList(Long userId, int page, int size) {
    // 从 Redis 获取
    Set<Object> followingSet = redisTemplate.opsForSet().members("relation:following:" + userId);
    if (followingSet != null) {
        return new ArrayList<>(followingSet); // 返回 Redis 中的数据
    }

    // 查询数据库
    Pageable pageable = PageRequest.of(page - 1, size);
    return relationRepository.findFollowingsByUserId(userId, pageable);
}
```

------

### **4. 获取粉丝列表**

```
public List<Long> getFollowersList(Long userId, int page, int size) {
    // 同样从 Redis 或数据库查询
    ...
}
```

------

### **5. 查询关注关系**

```
public boolean isFollowing(Long userId, Long followId) {
    // 从 Redis 查询
    Boolean isMember = redisTemplate.opsForSet().isMember("relation:following:" + userId, followId);
    if (isMember != null) {
        return isMember;
    }

    // 查询数据库
    return relationRepository.existsByUserIdAndFollowId(userId, followId);
}
```

------

## **性能优化**

1. Redis 缓存

   ：

   - 实现热点用户的关注列表和粉丝列表缓存。

2. 异步更新

   ：

   - 通过消息队列（如 Kafka），异步更新粉丝列表或通知 Feed 服务。

3. 分库分表

   ：

   - 当关注关系量非常大时，可以对 `user_relation` 表按用户 ID 或关注 ID 进行分库分表。

------

## **总结**

`Relation 服务` 的设计注重高效性和扩展性，采用 **Redis 缓存** 和 **关系表** 结合的方式，既能满足高并发查询需求，也能保证数据一致性。服务方法逻辑清晰，接口设计简单易用，可与其他服务（如 Feed 服务）无缝集成。

## 部署

### kafka

依次启动zookeeper和kafka

````bash
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

.\bin\windows\kafka-server-start.bat .\config\server.properties
````

