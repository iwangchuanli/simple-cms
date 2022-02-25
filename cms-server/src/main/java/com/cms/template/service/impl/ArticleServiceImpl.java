package com.cms.template.service.impl;

import com.cms.template.entity.Article;
import com.cms.template.mapper.ArticleMapper;
import com.cms.template.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author author
 * @since 2021-04-12
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
