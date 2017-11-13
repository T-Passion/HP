目标：旨在实现民间版虎扑



1. 架构：依照MVP进行
    1. 依据虎扑设计，将以下五个模块进行分Module开发，各个模块使用不同的技术方案
    2. 新闻module
    3. 比赛module
    4. 社区module
    5. 装备module
    6. 更多module
2. 组件化：注意这里将在不同组件中使用基础层中不同的库，因此需统一封装API
    1. 基础库base：各个接口基类的封装，以及基础框架搭建
    2. 网络库：
    3. 图片库：
    4. 事件通信
    5. 数据持久

3. 引用库：
    1. OkHttp／Retrofit
        1.
        2.
    2. RxJava2
        1.
    3. ButterKnife
        1. [入门介绍](https://github.com/JakeWharton/butterknife)
    4. glide/Picasso
        1.
    5. realm
        1.
    6. PullLoadMoreRecycleView
        1.
    7. Dagger2
        1. [入门介绍](http://www.jianshu.com/p/65737ac39c44)
    8. ARouter
        1. [入门介绍](https://github.com/alibaba/ARouter)
3. 使用git，并且进行代码审查husky以及lint-staged
