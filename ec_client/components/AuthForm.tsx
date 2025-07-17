"use client"

import type React from "react"

import { useState } from "react"
import Link from "next/link"
import { Eye, EyeOff } from "lucide-react"

interface AuthFormProps {
  type: "login" | "register" | "forgot-password"
}

export default function AuthForm({ type }: AuthFormProps) {
  const [showPassword, setShowPassword] = useState(false)
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    name: "",
  })
  const [errors, setErrors] = useState<Record<string, string>>({})

  const validateEmail = (email: string) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return emailRegex.test(email)
  }

  const validateForm = () => {
    const newErrors: Record<string, string> = {}

    if (!formData.email) {
      newErrors.email = "Email là bắt buộc"
    } else if (!validateEmail(formData.email)) {
      newErrors.email = "Email không hợp lệ"
    }

    if (type !== "forgot-password") {
      if (!formData.password) {
        newErrors.password = "Mật khẩu là bắt buộc"
      } else if (formData.password.length < 6) {
        newErrors.password = "Mật khẩu phải có ít nhất 6 ký tự"
      }
    }

    if (type === "register") {
      if (!formData.name) {
        newErrors.name = "Tên là bắt buộc"
      }
      if (formData.password !== formData.confirmPassword) {
        newErrors.confirmPassword = "Mật khẩu xác nhận không khớp"
      }
    }

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (validateForm()) {
      console.log("Form submitted:", formData)
      // Handle form submission here
    }
  }

  const getTitle = () => {
    switch (type) {
      case "login":
        return "Đăng nhập"
      case "register":
        return "Đăng ký"
      case "forgot-password":
        return "Quên mật khẩu"
    }
  }

  const getSubmitText = () => {
    switch (type) {
      case "login":
        return "Đăng nhập"
      case "register":
        return "Đăng ký"
      case "forgot-password":
        return "Gửi link đặt lại"
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center px-4 py-12">
      <div className="max-w-md w-full">
        <div className="card p-8">
          <div className="text-center mb-8">
            <h2 className="text-3xl font-bold text-gray-900">{getTitle()}</h2>
            <p className="mt-2 text-gray-600">
              {type === "login" && "Chào mừng bạn quay trở lại"}
              {type === "register" && "Tạo tài khoản mới"}
              {type === "forgot-password" && "Nhập email để đặt lại mật khẩu"}
            </p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-6">
            {type === "register" && (
              <div>
                <label htmlFor="name" className="block text-sm font-medium text-gray-700 mb-2">
                  Họ và tên
                </label>
                <input
                  id="name"
                  type="text"
                  value={formData.name}
                  onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                  className={`input-field ${errors.name ? "border-red-500" : ""}`}
                  placeholder="Nhập họ và tên"
                />
                {errors.name && <p className="mt-1 text-sm text-red-500">{errors.name}</p>}
              </div>
            )}

            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">
                Email
              </label>
              <input
                id="email"
                type="email"
                value={formData.email}
                onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                className={`input-field ${errors.email ? "border-red-500" : ""}`}
                placeholder="Nhập email"
              />
              {errors.email && <p className="mt-1 text-sm text-red-500">{errors.email}</p>}
            </div>

            {type !== "forgot-password" && (
              <div>
                <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-2">
                  Mật khẩu
                </label>
                <div className="relative">
                  <input
                    id="password"
                    type={showPassword ? "text" : "password"}
                    value={formData.password}
                    onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                    className={`input-field pr-12 ${errors.password ? "border-red-500" : ""}`}
                    placeholder="Nhập mật khẩu"
                  />
                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700"
                  >
                    {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
                  </button>
                </div>
                {errors.password && <p className="mt-1 text-sm text-red-500">{errors.password}</p>}
              </div>
            )}

            {type === "register" && (
              <div>
                <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700 mb-2">
                  Xác nhận mật khẩu
                </label>
                <input
                  id="confirmPassword"
                  type="password"
                  value={formData.confirmPassword}
                  onChange={(e) => setFormData({ ...formData, confirmPassword: e.target.value })}
                  className={`input-field ${errors.confirmPassword ? "border-red-500" : ""}`}
                  placeholder="Nhập lại mật khẩu"
                />
                {errors.confirmPassword && <p className="mt-1 text-sm text-red-500">{errors.confirmPassword}</p>}
              </div>
            )}

            <button type="submit" className="w-full btn-primary">
              {getSubmitText()}
            </button>
          </form>

          <div className="mt-6 text-center text-sm">
            {type === "login" && (
              <>
                <Link href="/forgot-password" className="text-black hover:underline">
                  Quên mật khẩu?
                </Link>
                <p className="mt-2 text-gray-600">
                  Chưa có tài khoản?{" "}
                  <Link href="/register" className="text-black hover:underline font-medium">
                    Đăng ký ngay
                  </Link>
                </p>
              </>
            )}
            {type === "register" && (
              <p className="text-gray-600">
                Đã có tài khoản?{" "}
                <Link href="/login" className="text-black hover:underline font-medium">
                  Đăng nhập
                </Link>
              </p>
            )}
            {type === "forgot-password" && (
              <p className="text-gray-600">
                Nhớ mật khẩu?{" "}
                <Link href="/login" className="text-black hover:underline font-medium">
                  Đăng nhập
                </Link>
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
