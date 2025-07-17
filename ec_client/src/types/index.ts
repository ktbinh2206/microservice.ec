export interface Product {
  id: string
  name: string
  description: string
  price: number
  originalPrice?: number
  stock: number
  categoryId: string
  sellerId: string
  images: string[]
  sizes: string[]
  colors: string[]
  rating: number
  reviewCount: number
  isNew?: boolean
  isSale?: boolean
  tags: string[]
  createdAt: string
}

export interface Category {
  id: string
  name: string
  slug: string
  image: string
  description: string
  productCount: number
}

export interface CartItem {
  id: string
  productId: string
  name: string
  price: number
  image: string
  quantity: number
  size?: string
  color?: string
}

export interface User {
  id: string
  name: string
  email: string
  avatar?: string
  phone?: string
  address?: Address
  createdAt: string
}

export interface Address {
  street: string
  city: string
  state: string
  zipCode: string
  country: string
}

export interface Order {
  id: string
  userId: string
  items: CartItem[]
  total: number
  status: "pending" | "processing" | "shipped" | "delivered" | "cancelled"
  shippingAddress: Address
  createdAt: string
  updatedAt: string
}

export interface Review {
  id: string
  productId: string
  userId: string
  userName: string
  rating: number
  comment: string
  createdAt: string
}

export interface Toast {
  id: string
  type: "success" | "error" | "warning" | "info"
  title: string
  message?: string
  duration?: number
}
