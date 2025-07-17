"use client"

import Link from "next/link"
import { useState } from "react"
import { Menu, X, ShoppingBag, User } from "lucide-react"

export default function Navbar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false)

  return (
    <nav className="fixed top-0 left-0 right-0 bg-white/95 backdrop-blur-md border-b border-gray-200 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <Link href="/" className="text-2xl font-bold text-black">
            ModernStore
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-8">
            <Link href="/" className="text-gray-700 hover:text-black transition-colors">
              Trang chủ
            </Link>
            <Link href="/products" className="text-gray-700 hover:text-black transition-colors">
              Sản phẩm
            </Link>
            <Link href="/categories" className="text-gray-700 hover:text-black transition-colors">
              Danh mục
            </Link>
            <Link href="/about" className="text-gray-700 hover:text-black transition-colors">
              Về chúng tôi
            </Link>
          </div>

          {/* Right side icons */}
          <div className="hidden md:flex items-center space-x-4">
            <Link href="/login" className="p-2 text-gray-700 hover:text-black transition-colors">
              <User size={20} />
            </Link>
            <button className="p-2 text-gray-700 hover:text-black transition-colors">
              <ShoppingBag size={20} />
            </button>
          </div>

          {/* Mobile menu button */}
          <button className="md:hidden p-2" onClick={() => setIsMenuOpen(!isMenuOpen)}>
            {isMenuOpen ? <X size={24} /> : <Menu size={24} />}
          </button>
        </div>

        {/* Mobile Navigation */}
        {isMenuOpen && (
          <div className="md:hidden py-4 border-t border-gray-200">
            <div className="flex flex-col space-y-4">
              <Link href="/" className="text-gray-700 hover:text-black transition-colors">
                Trang chủ
              </Link>
              <Link href="/products" className="text-gray-700 hover:text-black transition-colors">
                Sản phẩm
              </Link>
              <Link href="/categories" className="text-gray-700 hover:text-black transition-colors">
                Danh mục
              </Link>
              <Link href="/about" className="text-gray-700 hover:text-black transition-colors">
                Về chúng tôi
              </Link>
              <div className="flex items-center space-x-4 pt-4 border-t border-gray-200">
                <Link
                  href="/login"
                  className="flex items-center space-x-2 text-gray-700 hover:text-black transition-colors"
                >
                  <User size={20} />
                  <span>Đăng nhập</span>
                </Link>
                <button className="flex items-center space-x-2 text-gray-700 hover:text-black transition-colors">
                  <ShoppingBag size={20} />
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
