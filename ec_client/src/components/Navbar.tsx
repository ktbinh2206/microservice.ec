"use client"

import { useState } from "react"
import { Link, useLocation } from "react-router"

// Simple icons as SVG components
const MenuIcon = () => (
  <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
  </svg>
)

const XIcon = () => (
  <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
  </svg>
)

const ShoppingBagIcon = () => (
  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth={2}
      d="M16 11V7a4 4 0 00-8 0v4M5 9h14l-1 12H6L5 9z"
    />
  </svg>
)

const UserIcon = () => (
  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth={2}
      d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
    />
  </svg>
)

export default function Navbar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false)
  const location = useLocation()

  const isActive = (path: string) => location.pathname === path

  return (
    <nav className="fixed top-0 left-0 right-0 bg-white/95 backdrop-blur-md border-b border-gray-200 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <Link to="/" className="text-2xl font-bold text-black">
            ModernStore
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-8">
            <Link
              to="/"
              className={`transition-colors ${isActive("/") ? "text-black font-medium" : "text-gray-700 hover:text-black"}`}
            >
              Trang chủ
            </Link>
            <Link
              to="/products"
              className={`transition-colors ${isActive("/products") ? "text-black font-medium" : "text-gray-700 hover:text-black"}`}
            >
              Sản phẩm
            </Link>
            <a href="#categories" className="text-gray-700 hover:text-black transition-colors">
              Danh mục
            </a>
            <a href="#about" className="text-gray-700 hover:text-black transition-colors">
              Về chúng tôi
            </a>
          </div>

          {/* Right side icons */}
          <div className="hidden md:flex items-center space-x-4">
            <Link to="/login" className="p-2 text-gray-700 hover:text-black transition-colors">
              <UserIcon />
            </Link>
            <button className="p-2 text-gray-700 hover:text-black transition-colors">
              <ShoppingBagIcon />
            </button>
          </div>

          {/* Mobile menu button */}
          <button className="md:hidden p-2" onClick={() => setIsMenuOpen(!isMenuOpen)}>
            {isMenuOpen ? <XIcon /> : <MenuIcon />}
          </button>
        </div>

        {/* Mobile Navigation */}
        {isMenuOpen && (
          <div className="md:hidden py-4 border-t border-gray-200">
            <div className="flex flex-col space-y-4">
              <Link
                to="/"
                className={`transition-colors ${isActive("/") ? "text-black font-medium" : "text-gray-700 hover:text-black"}`}
                onClick={() => setIsMenuOpen(false)}
              >
                Trang chủ
              </Link>
              <Link
                to="/products"
                className={`transition-colors ${isActive("/products") ? "text-black font-medium" : "text-gray-700 hover:text-black"}`}
                onClick={() => setIsMenuOpen(false)}
              >
                Sản phẩm
              </Link>
              <a href="#categories" className="text-gray-700 hover:text-black transition-colors">
                Danh mục
              </a>
              <a href="#about" className="text-gray-700 hover:text-black transition-colors">
                Về chúng tôi
              </a>
              <div className="flex items-center space-x-4 pt-4 border-t border-gray-200">
                <Link
                  to="/login"
                  className="flex items-center space-x-2 text-gray-700 hover:text-black transition-colors"
                  onClick={() => setIsMenuOpen(false)}
                >
                  <UserIcon />
                  <span>Đăng nhập</span>
                </Link>
                <button className="flex items-center space-x-2 text-gray-700 hover:text-black transition-colors">
                  <ShoppingBagIcon />
                  <span>Giỏ hàng</span>
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </nav>
  )
}
