package com.example.nhac;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum role {
    ADMIN(1), // Vai trò quản trị viên, có quyền cao nhất trong hệ thống.
    USER(2); // Vai trò người dùng bình thường, có quyền hạn giới hạn.
    public final long value;
}