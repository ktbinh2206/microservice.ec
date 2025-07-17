import Image from "next/image"
import Link from "next/link"
import { categories } from "@/lib/data"
import { ArrowRight } from "lucide-react"

export default function HomePage() {
  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <section className="relative h-screen flex items-center justify-center bg-gradient-to-br from-gray-900 to-gray-700 text-white">
        <div className="absolute inset-0 bg-black/20"></div>
        <div className="relative z-10 text-center max-w-4xl mx-auto px-4">
          <h1 className="text-5xl md:text-7xl font-bold mb-6 leading-tight">
            Thời trang
            <br />
            <span className="text-gray-300">Hiện đại</span>
          </h1>
          <p className="text-xl md:text-2xl mb-8 text-gray-200 max-w-2xl mx-auto">
            Khám phá bộ sưu tập thời trang tối giản với chất lượng cao và thiết kế tinh tế
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Link href="/products" className="btn-primary inline-flex items-center justify-center">
              Khám phá ngay
              <ArrowRight className="ml-2" size={20} />
            </Link>
            <Link href="/categories" className="btn-secondary">
              Xem danh mục
            </Link>
          </div>
        </div>
      </section>

      {/* Featured Categories */}
      <section className="py-20 px-4">
        <div className="max-w-7xl mx-auto">
          <div className="text-center mb-16">
            <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">Danh mục nổi bật</h2>
            <p className="text-xl text-gray-600 max-w-2xl mx-auto">
              Tìm kiếm sản phẩm yêu thích trong các danh mục được tuyển chọn kỹ lưỡng
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
            {categories.map((category) => (
              <Link key={category.id} href={`/products?category=${category.id}`} className="group">
                <div className="card overflow-hidden">
                  <div className="relative aspect-square">
                    <Image
                      src={category.image || "/placeholder.svg"}
                      alt={category.name}
                      fill
                      className="object-cover group-hover:scale-105 transition-transform duration-300"
                    />
                  </div>
                  <div className="p-6 text-center">
                    <h3 className="text-xl font-semibold text-gray-900 group-hover:text-gray-700 transition-colors">
                      {category.name}
                    </h3>
                  </div>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 bg-gray-900 text-white">
        <div className="max-w-4xl mx-auto text-center px-4">
          <h2 className="text-4xl md:text-5xl font-bold mb-6">Sẵn sàng khám phá?</h2>
          <p className="text-xl text-gray-300 mb-8 max-w-2xl mx-auto">
            Tham gia cộng đồng những người yêu thích thời trang tối giản và chất lượng cao
          </p>
          <Link href="/register" className="btn-primary">
            Đăng ký ngay
          </Link>
        </div>
      </section>
    </div>
  )
}
