## 列出数据库
SELECT datname FROM pg_database;
## 列出数据表
SELECT * FROM pg_tables WHERE schemaname = 'public';
## 查看表结构
SELECT table_name,column_name,data_type,column_default FROM information_schema.columns WHERE table_name = 'actor';
