import Image from "next/image"
import type { Product } from "@/lib/data"
import { ShoppingCart } from "lucide-react"

interface ProductCardProps {
  product: Product
}

export default function ProductCard({ product }: ProductCardProps) {
  const formatPrice = (price: number) => {
    return new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(price)
  }

  return (
    <div className="card p-0 overflow-hidden group">
      <div className="relative aspect-square overflow-hidden">
        <Image
          src={product.image || "/placeholder.svg"}
          alt={product.name}
          fill
          className="object-cover group-hover:scale-105 transition-transform duration-300"
        />
        {product.stock < 10 && (
          <div className="absolute top-4 left-4 bg-red-500 text-white px-3 py-1 rounded-full text-sm font-medium">
            Sắp hết hàng
          </div>
        )}
      </div>

      <div className="p-6">
        <h3 className="text-lg font-semibold text-gray-900 mb-2 line-clamp-1">{product.name}</h3>
        <p className="text-gray-600 text-sm mb-4 line-clamp-2">{product.description}</p>

        <div className="flex items-center justify-between">
          <span className="text-xl font-bold text-gray-900">{formatPrice(product.price)}</span>
          <button className="bg-black text-white p-3 rounded-full hover:bg-gray-800 transition-colors duration-200 group">
            <ShoppingCart size={18} className="group-hover:scale-110 transition-transform" />
          </button>
        </div>

        <div className="mt-4 text-sm text-gray-500">Còn lại: {product.stock} sản phẩm</div>
      </div>
    </div>
  )
}
