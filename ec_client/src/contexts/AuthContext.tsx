"use client"

import { createContext, useContext, useState, useEffect, type ReactNode } from "react"
import type { User } from "../types"
import { useToast } from "../hooks/useToast"

interface AuthState {
  user: User | null
  isLoading: boolean
  isAuthenticated: boolean
}

interface AuthContextType extends AuthState {
  login: (email: string, password: string) => Promise<boolean>
  register: (name: string, email: string, password: string) => Promise<boolean>
  logout: () => void
  updateProfile: (data: Partial<User>) => void
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

// Mock user data for demo
const mockUsers: User[] = [
  {
    id: "1",
    name: "Nguyễn Văn A",
    email: "user@example.com",
    avatar: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=100&h=100&fit=crop&crop=face",
    phone: "0123456789",
    address: {
      street: "123 Đường ABC",
      city: "Hồ Chí Minh",
      state: "Hồ Chí Minh",
      zipCode: "70000",
      country: "Việt Nam",
    },
    createdAt: "2024-01-01T00:00:00Z",
  },
]

export function AuthProvider({ children }: { children: ReactNode }) {
  const [state, setState] = useState<AuthState>({
    user: null,
    isLoading: true,
    isAuthenticated: false,
  })

  const { showToast } = useToast()

  // Check for saved user on mount
  useEffect(() => {
    const savedUser = localStorage.getItem("user")
    if (savedUser) {
      try {
        const user = JSON.parse(savedUser)
        setState({
          user,
          isLoading: false,
          isAuthenticated: true,
        })
      } catch (error) {
        console.error("Error loading user from localStorage:", error)
        setState((prev) => ({ ...prev, isLoading: false }))
      }
    } else {
      setState((prev) => ({ ...prev, isLoading: false }))
    }
  }, [])

  const login = async (email: string, password: string): Promise<boolean> => {
    try {
      // Mock login - in real app, this would be an API call
      await new Promise((resolve) => setTimeout(resolve, 1000))

      if (email === "user@example.com" && password === "password") {
        const user = mockUsers[0]
        setState({
          user,
          isLoading: false,
          isAuthenticated: true,
        })
        localStorage.setItem("user", JSON.stringify(user))
        showToast({
          type: "success",
          title: "Đăng nhập thành công",
          message: `Chào mừng ${user.name}!`,
        })
        return true
      } else {
        showToast({
          type: "error",
          title: "Đăng nhập thất bại",
          message: "Email hoặc mật khẩu không chính xác",
        })
        return false
      }
    } catch (error) {
      showToast({
        type: "error",
        title: "Lỗi đăng nhập",
        message: "Có lỗi xảy ra khi đăng nhập",
      })
      return false
    }
  }

  const register = async (name: string, email: string, password: string): Promise<boolean> => {
    try {
      // Mock registration - in real app, this would be an API call
      await new Promise((resolve) => setTimeout(resolve, 1000))

      const newUser: User = {
        id: Date.now().toString(),
        name,
        email,
        createdAt: new Date().toISOString(),
      }

      setState({
        user: newUser,
        isLoading: false,
        isAuthenticated: true,
      })
      localStorage.setItem("user", JSON.stringify(newUser))
      showToast({
        type: "success",
        title: "Đăng ký thành công",
        message: `Chào mừng ${name}!`,
      })
      return true
    } catch (error) {
      showToast({
        type: "error",
        title: "Lỗi đăng ký",
        message: "Có lỗi xảy ra khi đăng ký",
      })
      return false
    }
  }

  const logout = () => {
    setState({
      user: null,
      isLoading: false,
      isAuthenticated: false,
    })
    localStorage.removeItem("user")
    showToast({
      type: "info",
      title: "Đã đăng xuất",
      message: "Hẹn gặp lại bạn!",
    })
  }

  const updateProfile = (data: Partial<User>) => {
    if (state.user) {
      const updatedUser = { ...state.user, ...data }
      setState((prev) => ({ ...prev, user: updatedUser }))
      localStorage.setItem("user", JSON.stringify(updatedUser))
      showToast({
        type: "success",
        title: "Cập nhật thành công",
        message: "Thông tin cá nhân đã được cập nhật",
      })
    }
  }

  return (
    <AuthContext.Provider
      value={{
        ...state,
        login,
        register,
        logout,
        updateProfile,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider")
  }
  return context
}
